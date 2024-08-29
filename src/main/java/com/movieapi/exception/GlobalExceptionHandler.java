package com.movieapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MovieNotFoundException.class)
	//problemdtl is helpful in creating customised responses
	public ProblemDetail handleMovieNotFoundException(MovieNotFoundException ex) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
	}
	
	@ExceptionHandler(FileAlreadyExistsException.class)
	public ProblemDetail handleFileNotFoundException(FileAlreadyExistsException ex) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
	}
	
	
	@ExceptionHandler(EmptyFileException.class)
	public ProblemDetail handleEmptyFileException(EmptyFileException ex) {
		return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
	}
	
}
