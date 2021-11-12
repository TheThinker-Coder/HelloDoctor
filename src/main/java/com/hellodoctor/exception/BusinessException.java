package com.hellodoctor.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class BusinessException extends RuntimeException  {

	public BusinessException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BusinessException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
		
	
	

	
	


}
