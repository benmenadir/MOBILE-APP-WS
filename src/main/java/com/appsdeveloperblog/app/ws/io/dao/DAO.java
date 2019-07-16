package com.appsdeveloperblog.app.ws.io.dao;

import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

import java.util.List;

public interface DAO {
    void openConnection();
    UserDTO getUserByUserName(String userName);
    UserDTO saveUser (UserDTO user);
    UserDTO getUser (String id);
    void updateUser (UserDTO userProfile);
    List<UserDTO> getUsers (int start, int limit);
    void deleteUser (UserDTO userProfile);
    void closeConnection();

}
