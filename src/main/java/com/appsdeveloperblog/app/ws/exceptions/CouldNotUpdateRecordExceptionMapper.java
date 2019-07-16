package com.appsdeveloperblog.app.ws.exceptions;

import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessage;
import com.appsdeveloperblog.app.ws.ui.model.response.ErrorMessages;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class CouldNotUpdateRecordExceptionMapper implements ExceptionMapper<CouldNotUpdateRecordException> {
    @Override
    public Response toResponse (CouldNotUpdateRecordException exception) {
        ErrorMessage errorMessage = new ErrorMessage(
                exception.getMessage(),
                ErrorMessages.COULD_NOT_UPDATE_RECORD.name(),
                "http://appsdeveloperblog.com"
        );
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }
}
