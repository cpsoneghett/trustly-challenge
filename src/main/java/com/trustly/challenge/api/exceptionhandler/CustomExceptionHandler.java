package com.trustly.challenge.api.exceptionhandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.trustly.challenge.api.exceptionhandler.exception.NotAGitHubRepositoryUrlException;
import com.trustly.challenge.api.exceptionhandler.exception.RepositoryNameOrOwnerMissingException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler({ InterruptedException.class })
	private ResponseEntity<Object> handleInterruptedException(InterruptedException ex) {

		String userMessage = messageSource.getMessage("general.user.message", null, LocaleContextHolder.getLocale());
		String developerMessage = ex.toString();

		List<Error> erros = Arrays.asList(new Error(userMessage, developerMessage));

		return ResponseEntity.badRequest().body(erros);

	}

	@ExceptionHandler({ IOException.class })
	private ResponseEntity<Object> handleIOException(IOException ex) {

		String userMessage = messageSource.getMessage("general.user.message", null, LocaleContextHolder.getLocale());
		String developerMessage = ex.toString();

		List<Error> erros = Arrays.asList(new Error(userMessage, developerMessage));

		return ResponseEntity.badRequest().body(erros);

	}

	@ExceptionHandler({ NotAGitHubRepositoryUrlException.class })
	private ResponseEntity<Object> handleNotAGitHubRepositoryUrlException(NotAGitHubRepositoryUrlException ex) {

		String userMessage = "This is not a valid GitHub Repository. The URL must contain 'https://github.com'";
		String developerMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();

		List<Error> erros = Arrays.asList(new Error(userMessage, developerMessage));

		return ResponseEntity.badRequest().body(erros);

	}

	@ExceptionHandler({ RepositoryNameOrOwnerMissingException.class })
	private ResponseEntity<Object> handleRepositoryNameOrOwnerMissingException(
			RepositoryNameOrOwnerMissingException ex) {

		String userMessage = ex.getErrorMessage();
		String developerMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();

		List<Error> erros = Arrays.asList(new Error(userMessage, developerMessage));

		return ResponseEntity.badRequest().body(erros);

	}

	public static class Error {

		private String userMessage;
		private String developerMessage;

		public Error(String userMessage, String developerMessage) {
			super();
			this.userMessage = userMessage;
			this.developerMessage = developerMessage;
		}

		public String getDeveloperMessage() {
			return developerMessage;
		}

		public String getUserMessage() {
			return userMessage;
		}

	}
}
