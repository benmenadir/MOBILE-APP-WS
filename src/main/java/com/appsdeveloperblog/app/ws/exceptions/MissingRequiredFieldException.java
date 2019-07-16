package com.appsdeveloperblog.app.ws.exceptions;

public class MissingRequiredFieldException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 5776681206288518465L;

	public MissingRequiredFieldException (String message) {
		super(message);
	}
	

}
