package com.appsdeveloperblog.app.ws.ui.entrypoint;


import com.appsdeveloperblog.app.ws.annotations.Secured;
import com.appsdeveloperblog.app.ws.service.UsersService;
import com.appsdeveloperblog.app.ws.service.impl.UsersServiceImpl;
import com.appsdeveloperblog.app.ws.shared.dto.UserDTO;
import com.appsdeveloperblog.app.ws.ui.model.request.CreateUserRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.request.UpdateUserRequestModel;
import com.appsdeveloperblog.app.ws.ui.model.response.DeleteUserProfileResponseModel;
import com.appsdeveloperblog.app.ws.ui.model.response.RequestOperation;
import com.appsdeveloperblog.app.ws.ui.model.response.ResponseStatus;
import com.appsdeveloperblog.app.ws.ui.model.response.UserProfileRest;
import org.springframework.beans.BeanUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/users")
public class UserEntryPoint {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserProfileRest createUser (CreateUserRequestModel requestObject) {
        UserProfileRest returnValue = new UserProfileRest();

        // Prepare UserDTO
        UserDTO userDto = new UserDTO();
        BeanUtils.copyProperties(requestObject, userDto);

        // Create new user
        UsersService userService = new UsersServiceImpl();
        UserDTO createdUserProfile = userService.createUser(userDto);

        //Prepare response
        BeanUtils.copyProperties(createdUserProfile, returnValue);
        return returnValue;
    }

    @Secured
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserProfileRest getUserProfile (@PathParam("id") String id) {
        UserProfileRest returnValue = null;

        UsersService usersService = new UsersServiceImpl();
        UserDTO userProfile = usersService.getUser(id);

        returnValue = new UserProfileRest();
        BeanUtils.copyProperties(userProfile, returnValue);

        return returnValue;
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<UserProfileRest> getUsers (@DefaultValue("0") @QueryParam("start") int start, @DefaultValue("50") @QueryParam("limit") int limit) {

        UsersService usersService = new UsersServiceImpl();
        List<UserDTO> users = usersService.getUsers(start, limit);

        // Prepare return Value
        List<UserProfileRest> returnValue = new ArrayList<UserProfileRest>();
        for (UserDTO userDTO : users) {
            UserProfileRest userModel = new UserProfileRest();
            BeanUtils.copyProperties(userDTO, userModel);
            userModel.setHref("/users/" + userDTO.getUserId());

            returnValue.add(userModel);

        }


        return returnValue;
    }

    @Secured
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public UserProfileRest updateUserDetails (@PathParam("id") String id, UpdateUserRequestModel userDetails) {

        UsersService userService = new UsersServiceImpl();
        UserDTO storedUserDetails = userService.getUser(id);

        // Set only those fields you would like to be updated with this request
        if (userDetails.getFirstName() != null && !userDetails.getFirstName().isEmpty()) {
            storedUserDetails.setFirstName(userDetails.getFirstName());
        }
        storedUserDetails.setLastName(userDetails.getLastName());

        // Update User Details
        userService.updateUserDetails(storedUserDetails);

        // Prepare return value
        UserProfileRest returnValue = new UserProfileRest();
        BeanUtils.copyProperties(storedUserDetails, returnValue);


        return returnValue;

    }

    @Secured
    @DELETE
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public DeleteUserProfileResponseModel deleteUserProfile (@PathParam("id") String id) {
        DeleteUserProfileResponseModel returnValue = new DeleteUserProfileResponseModel();
        returnValue.setRequestOperation(RequestOperation.DELETE);

        UsersService userService = new UsersServiceImpl();
        UserDTO storedUserDetails = userService.getUser(id);

        userService.deleteUser(storedUserDetails);

        returnValue.setResponseStatus(ResponseStatus.SUCCESS);

        return returnValue;
    }
}
