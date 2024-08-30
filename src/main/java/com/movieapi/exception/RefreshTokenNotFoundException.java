package com.movieapi.exception;

import lombok.experimental.SuperBuilder;

public class RefreshTokenNotFoundException extends RuntimeException {

	public RefreshTokenNotFoundException(String message){
		super(message);
	}
	
}
