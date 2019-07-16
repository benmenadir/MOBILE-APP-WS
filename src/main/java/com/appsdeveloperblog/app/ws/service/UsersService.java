package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

public interface UsersService {

    UserDTO createUser (UserDTO userDto);
    UserDTO getUser (String id);
    UserDTO getUserByUserName (String userName);
}
