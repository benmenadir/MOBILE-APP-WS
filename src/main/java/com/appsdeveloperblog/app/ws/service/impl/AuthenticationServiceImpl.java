package com.appsdeveloperblog.app.ws.service.impl;

import com.appsdeveloperblog.app.ws.exceptions.AuthenticationException;
import com.appsdeveloperblog.app.ws.io.dao.DAO;
import com.appsdeveloperblog.app.ws.io.dao.impl.MySQLDAO;
import com.appsdeveloperblog.app.ws.service.AuthenticationService;
import com.appsdeveloperblog.app.ws.service.UsersService;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;
import com.appsdeveloperblog.app.ws.utils.UserProfileUtils;


import java.util.Base64;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.security.spec.InvalidKeySpecException;



public class AuthenticationServiceImpl implements AuthenticationService {


    public static final Logger LOG = Logger.getLogger(AuthenticationServiceImpl.class.getName());

    DAO database;

    @Override
    public UserDTO authentication (String userName, String password) throws AuthenticationException {
        UsersService usersService = new UsersServiceImpl();
        UserDTO storeUser =usersService.getUserByUserName(userName);

        if(storeUser == null){
            throw  new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessages());
        }

        String encryptedPassword = null;
        encryptedPassword = new UserProfileUtils().generateSecurePassword(password, storeUser.getSalt());

        boolean authenticated = false;
        if(encryptedPassword != null && encryptedPassword.equalsIgnoreCase(storeUser.getEncryptedPassword())){
            if(userName != null && userName.equalsIgnoreCase(storeUser.getEmail())){
                authenticated = true;
            }
        }

        if(!authenticated){
            throw  new AuthenticationException(ErrorMessages.AUTHENTICATION_FAILED.getErrorMessages());
        }

        return storeUser;
    }

    public String issueAccessToken (UserDTO userProfile) throws AuthenticationException {
        String returnValue =null;

        String newSaltAsPostfix = userProfile.getSalt();
        String accessTokenMaterial = userProfile.getUserId()+newSaltAsPostfix;

        byte [] encryptedAccessToken = null;

        try {
            encryptedAccessToken = new UserProfileUtils().encrypt(userProfile.getEncryptedPassword(), accessTokenMaterial );
        }catch (InvalidKeySpecException ex){
            LOG.log(Level.SEVERE, null, ex);
            throw new AuthenticationException("Failed to issue secure acess token");
        }


        String encryptedAccessTokenBase64Encoded = Base64.getEncoder().encodeToString(encryptedAccessToken);

        // Split token into equal parts
        int tokenLength = encryptedAccessTokenBase64Encoded.length();

        String tokenToSaveToDatabase = encryptedAccessTokenBase64Encoded.substring(0, tokenLength / 2);
        returnValue = encryptedAccessTokenBase64Encoded.substring(tokenLength / 2, tokenLength);

        userProfile.setToken(tokenToSaveToDatabase);
        updateUserProfile(userProfile);

        return returnValue;
    }

    private void updateUserProfile(UserDTO userProfile) {
        this.database = new MySQLDAO();
        try {
            database.openConnection();
            database.updateUser(userProfile);
        } finally {
            database.closeConnection();
        }
    }

    @Override
    public void resetSecurityCridentials (String password, UserDTO userProfile) {

        // Generate a new Salt

        UserProfileUtils userUtils = new UserProfileUtils();
        String salt = userUtils.getSalt(30);


        // Generate a new Password

        String securePAssword = userUtils.generateSecurePassword(password, salt);
        userProfile.setSalt(salt);
        userProfile.setEncryptedPassword(securePAssword);

        // Update a new Profile

        updateUserProfile(userProfile);

    }
}
