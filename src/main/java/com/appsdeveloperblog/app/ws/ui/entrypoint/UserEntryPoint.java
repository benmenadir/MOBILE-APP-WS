package com.appsdeveloperblog.app.ws.ui.entrypoint;


import com.appsdeveloperblog.app.ws.annotations.Secured;
import com.appsdeveloperblog.app.ws.service.UsersService;
import com.appsdeveloperblog.app.ws.service.impl.UsersServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.request.CreateUserRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.UserProfileRest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;

import java.util.List;

@Path("/users")
public class UserEntryPoint {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON,  MediaType.APPLICATION_XML} )
	public UserProfileRest createUser(CreateUserRequestModel requestObject) {
		UserProfileRest returnValue = new UserProfileRest();

		// Prepare UserDTO
		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(requestObject, userDto);

		// Create new user
		UsersService userService = new UsersServiceImpl();
		UserDTO createdUserProfile = userService.createUser(userDto);

		//Prepare response
		BeanUtils.copyProperties(createdUserProfile, returnValue);
		return returnValue ;
	}

	@Secured
	@GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON,  MediaType.APPLICATION_XML} )
	public UserProfileRest getUserProfile (@PathParam("id") String id){
        UserProfileRest returnValue = null;

        UsersService usersService = new UsersServiceImpl();
        UserDTO userProfile = usersService.getUser(id);

        returnValue = new UserProfileRest();
        BeanUtils.copyProperties(userProfile, returnValue);

        return returnValue;
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON,  MediaType.APPLICATION_XML} )
    public List<UserProfileRest> getUsers(@QueryParam("start") int start, @QueryParam("limit") int limit){
        List<UserProfileRest> returnValue = null;



        return returnValue;
    }

	
}
