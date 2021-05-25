package com.trustly.challenge.api.exceptionhandler.exception;

public class TooManyRequestsException extends Exception {

	private static final long serialVersionUID = 6952539355908145286L;

	private final String errorMessage;

	public TooManyRequestsException(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
