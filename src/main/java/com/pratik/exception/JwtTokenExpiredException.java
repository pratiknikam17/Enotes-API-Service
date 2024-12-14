package com.pratik.exception;

public class JwtTokenExpiredException extends RuntimeException {

	public JwtTokenExpiredException(String message) {
		super(message);
		
	}

}
 