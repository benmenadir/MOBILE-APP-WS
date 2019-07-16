package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

import java.util.List;

public interface UsersService {

    UserDTO createUser (UserDTO userDto);
    UserDTO getUser (String id);
    UserDTO getUserByUserName (String userName);
    List<UserDTO> getUsers(int start, int limit);
    void updateUserDetails (UserDTO storedUserDetails);
    void deleteUser (UserDTO storedUserDetails);
}
