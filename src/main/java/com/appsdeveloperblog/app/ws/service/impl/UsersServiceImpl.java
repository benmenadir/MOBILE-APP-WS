package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.exceptions.CouldNotCreateRecordException;
import com.appsdeveloperblog.app.ws.exceptions.CouldNotDeleteRecordException;
import com.appsdeveloperblog.app.ws.exceptions.CouldNotUpdateRecordException;
import com.appsdeveloperblog.app.ws.exceptions.NoRecordFoundException;
import com.appsdeveloperblog.app.ws.io.dao.DAO;
import com.appsdeveloperblog.app.ws.io.dao.impl.MySQLDAO;
import com.appsdeveloperblog.app.ws.service.UsersService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.utils.UserProfileUtils;

import java.util.List;

public class UsersServiceImpl implements UsersService {

    DAO database;

    public UsersServiceImpl ( ) {
        this.database = new MySQLDAO();
    }

    UserProfileUtils userProfileUtils = new UserProfileUtils();

    @Override
    public UserDTO createUser (UserDTO user) {

        UserDTO returnValue = new UserDTO();

        // Validate the required fields

        userProfileUtils.validateRequiredFields(user);

        // Check if user already exists

        UserDTO existingUser = this.getUserByUserName(user.getEmail());

        if (existingUser != null) {
            throw new CouldNotCreateRecordException(ErrorMessages.RECORD_ALREADY_EXISTS.name());
        }

        // Generate secure public user id
        String userId = userProfileUtils.generateUserId(30);
        user.setUserId(userId);

        // Generate salt

        String salt = userProfileUtils.getSalt(30);
        // Generate secure password
        String encryptedPassword = userProfileUtils.generateSecurePassword(user.getPassword(), salt);
        user.setSalt(salt);
        user.setEncryptedPassword(encryptedPassword);

        // Record data into a database
        returnValue = this.saveUser(user);
        // return back the user profile

        return returnValue;
    }

    @Override
    public UserDTO getUser (String id) {
        UserDTO returnValue = null;
        try {
            this.database.openConnection();
            returnValue = this.database.getUser(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new NoRecordFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessages());

        } finally {
            this.database.closeConnection();
        }


        return returnValue;
    }

    private UserDTO saveUser (UserDTO user) {
        UserDTO returnValue = null;


        try {
            this.database.openConnection();
            returnValue = this.database.saveUser(user);

        } finally {
            this.database.closeConnection();
        }

        return returnValue;
    }

    @Override
    public UserDTO getUserByUserName (String userName) {
        UserDTO userDto = null;

        if (userName == null && userName.isEmpty()) {
            return userDto;
        }
        try {
            this.database.openConnection();
            userDto = this.database.getUserByUserName(userName);

        } finally {
            this.database.closeConnection();
        }

        return userDto;
    }

    @Override
    public List<UserDTO> getUsers (int start, int limit) {
        List<UserDTO> users = null;
        // Get users from database
        try {
            this.database.openConnection();
            users = this.database.getUsers(start, limit);
        } finally {
            this.database.closeConnection();
        }

        return users;
    }
    public void updateUserDetails(UserDTO userDetails) {
        try {
            // Connect to database
            this.database.openConnection();
            // Update User Details
            this.database.updateUser(userDetails);

        } catch (Exception ex) {
            throw new CouldNotUpdateRecordException(ex.getMessage());
        } finally {
            this.database.closeConnection();
        }
    }

    @Override
    public void deleteUser (UserDTO userDto) {
        try {
            this.database.openConnection();
            this.database.deleteUser(userDto);
        } catch (Exception ex) {
            throw new CouldNotDeleteRecordException(ex.getMessage());
        } finally {
            this.database.closeConnection();
        }

        // Verify that user is deleted
        try {
            userDto = getUser(userDto.getUserId());
        } catch (NoRecordFoundException ex) {
            userDto = null;
        }

        if (userDto != null) {
            throw new CouldNotDeleteRecordException(
                    ErrorMessages.COULD_NOT_DELETE_RECORD.getErrorMessages());
        }
    }
}
