package com.appsdeveloperblog.app.ws.ui.entrypoint;

import com.appsdeveloperblog.app.ws.service.AuthenticationService;
import com.appsdeveloperblog.app.ws.service.impl.AuthenticationServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.request.LoginCredentials;
import com.appsdeveloperblog.app.ws.ui.model.response.AuthenticationDetails;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/authentication")
public class AuthenticationEntryPoint {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public AuthenticationDetails userLogin(LoginCredentials loginCredentials){


        AuthenticationDetails returnValue = new AuthenticationDetails();
        AuthenticationService authenticationService =new AuthenticationServiceImpl();
        UserDTO authenticationUser =authenticationService.authentication(loginCredentials.getUserName(), loginCredentials.getUserPassword());

        // Reset Access Token
        authenticationService.resetSecurityCridentials(loginCredentials.getUserPassword(), authenticationUser);


        String accessToken = authenticationService.issueAccessToken(authenticationUser);
        returnValue.setId(authenticationUser.getUserId());
        returnValue.setToken(accessToken);

        return returnValue;
    }
}
