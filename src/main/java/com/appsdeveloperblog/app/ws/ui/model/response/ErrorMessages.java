package com.appsdeveloperblog.app.ws.ui.model.response;

public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("Missing required field, Please check documentation for required fields"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_RECORD_FOUND("Record with provider id is not found"),
    AUTHENTICATION_FAILED("Authentication failed"),
    COULD_NOT_UPDATE_RECORD("Could not updat record"),
    COULD_NOT_DELETE_RECORD("Could not delete record");


    private String errorMessages;

    ErrorMessages (String errorMessages) {
        this.errorMessages = errorMessages;
    }

    /**
     * @return the errorMessages
     */
    public String getErrorMessages ( ) {
        return errorMessages;
    }

    /**
     * @param errorMessages the errosMessages to set
     */
    public void setErrorMessages (String errorMessages) {
        this.errorMessages = errorMessages;
    }


}
