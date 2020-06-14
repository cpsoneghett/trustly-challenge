package com.trustly.challenge.api.exceptionhandler.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RepositoryNameOrOwnerMissingException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final String errorMessage;


}
