package com.appsdeveloperblog.app.ws.io.dao;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

public interface DAO {
    void openConnection();
    UserDTO getUserByUserName(String userName);
    UserDTO saveUser (UserDTO user);
    UserDTO getUser (String id);
    void closeConnection();
    void updateUser (UserDTO userProfile);
}
