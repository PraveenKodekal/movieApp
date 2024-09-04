package com.movieapi.exception;

public class OtpIsNotValidException extends RuntimeException{

	public OtpIsNotValidException(String message) {
		super("Invalid Otp");
	}
	
}
