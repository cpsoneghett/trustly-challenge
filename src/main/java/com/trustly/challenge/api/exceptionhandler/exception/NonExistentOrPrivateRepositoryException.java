package com.trustly.challenge.api.exceptionhandler.exception;

public class NonExistentOrPrivateRepositoryException extends Exception {

	private static final long serialVersionUID = 1477226591002505856L;

	private final String errorMessage;

	public NonExistentOrPrivateRepositoryException(String messageError) {
		this.errorMessage = messageError;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
