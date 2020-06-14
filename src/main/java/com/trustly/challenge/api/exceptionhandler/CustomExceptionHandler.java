package com.trustly.challenge.api.exceptionhandler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.trustly.challenge.api.exceptionhandler.exception.NotAGitHubRepositoryUrlException;
import com.trustly.challenge.api.exceptionhandler.exception.RepositoryNameOrOwnerMissingException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable( HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request ) {

		String userMessage = messageSource.getMessage( "invalid.message", null, LocaleContextHolder.getLocale() );
		String developerMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();

		List<Error> erros = Arrays.asList( new Error( userMessage, developerMessage ) );

		return handleExceptionInternal( ex, erros, headers, HttpStatus.BAD_REQUEST, request );
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request ) {

		List<Error> errors = createListOfErrors( ex.getBindingResult() );

		return handleExceptionInternal( ex, errors, headers, HttpStatus.BAD_REQUEST, request );
	}

	private List<Error> createListOfErrors( BindingResult bindingResult ) {
		List<Error> erros = new ArrayList<>();

		for ( FieldError fieldError : bindingResult.getFieldErrors() ) {
			String userMessage = messageSource.getMessage( fieldError, LocaleContextHolder.getLocale() );
			String developerMessage = fieldError.toString();

			erros.add( new Error( userMessage, developerMessage ) );
		}

		return erros;
	}

	@ExceptionHandler( { FileNotFoundException.class } )
	private ResponseEntity<Object> handleFileNotFoundException( FileNotFoundException ex ) {

		String userMessage = "Repository not found.";
		String developerMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();

		List<Error> erros = Arrays.asList( new Error( userMessage, developerMessage ) );

		return ResponseEntity.badRequest().body( erros );

	}

	@ExceptionHandler( { NotAGitHubRepositoryUrlException.class } )
	private ResponseEntity<Object> handleNotAGitHubRepositoryUrlException( NotAGitHubRepositoryUrlException ex ) {

		String userMessage = "This is not a valid GitHub Repository. The URL must contain 'https://github.com'";
		String developerMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();

		List<Error> erros = Arrays.asList( new Error( userMessage, developerMessage ) );

		return ResponseEntity.badRequest().body( erros );

	}

	@ExceptionHandler( { RepositoryNameOrOwnerMissingException.class } )
	private ResponseEntity<Object> handleRepositoryNameOrOwnerMissingException( RepositoryNameOrOwnerMissingException ex ) {

		String userMessage = ex.getErrorMessage();
		String developerMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();

		List<Error> erros = Arrays.asList( new Error( userMessage, developerMessage ) );

		return ResponseEntity.badRequest().body( erros );

	}

	@AllArgsConstructor
	@Getter
	public static class Error {

		private String userMessage;
		private String developerMessage;

	}
}
