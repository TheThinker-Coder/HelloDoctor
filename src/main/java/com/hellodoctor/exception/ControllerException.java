package com.hellodoctor.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;


public class ControllerException extends RuntimeException {

	public ControllerException() {
		super();
		
	}

	public ControllerException(String message) {
		super(message);
		
	}
	
}
