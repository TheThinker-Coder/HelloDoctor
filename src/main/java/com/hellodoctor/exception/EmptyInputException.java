package com.hellodoctor.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;


public class EmptyInputException extends RuntimeException {

	public EmptyInputException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmptyInputException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	

}