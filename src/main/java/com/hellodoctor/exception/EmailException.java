package com.hellodoctor.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;


public class EmailException extends RuntimeException {

	public EmailException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmailException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
}