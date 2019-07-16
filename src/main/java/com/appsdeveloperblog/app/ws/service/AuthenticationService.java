package com.appsdeveloperblog.app.ws.service;

import com.appsdeveloperblog.app.ws.exceptions.AuthenticationException;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;

public interface AuthenticationService {

    UserDTO authentication (String userName, String password) throws AuthenticationException;
    String issueAccessToken(UserDTO userProfile) throws  AuthenticationException;

    public void resetSecurityCridentials (String password, UserDTO userProfile);
}
