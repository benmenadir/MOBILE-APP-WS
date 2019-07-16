package com.appsdeveloperblog.app.ws.exceptions;

public class CouldNotDeleteRecordException extends RuntimeException {

    private static final long serialVersionUID = 2707879369312336055L;

    public CouldNotDeleteRecordException (String message) {
        super(message);
    }
}
