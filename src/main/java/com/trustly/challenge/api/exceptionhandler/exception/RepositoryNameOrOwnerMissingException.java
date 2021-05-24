package com.trustly.challenge.api.exceptionhandler.exception;

public class RepositoryNameOrOwnerMissingException extends RuntimeException {

	private static final long serialVersionUID = -6199244408224287617L;

	private final String errorMessage;

	public RepositoryNameOrOwnerMissingException(String messageError) {
		this.errorMessage = messageError;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
