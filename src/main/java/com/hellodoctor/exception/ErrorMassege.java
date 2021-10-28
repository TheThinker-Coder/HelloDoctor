package com.hellodoctor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class ErrorMassege {

	private String errorMassege;
	private String errorDiscription;
	private HttpStatus errorCode;
	public String getErrorMassege() {
		return errorMassege;
	}
	public void setErrorMassege(String errorMassege) {
		this.errorMassege = errorMassege;
	}
	public String getErrorDiscription() {
		return errorDiscription;
	}
	public void setErrorDiscription(String errorDiscription) {
		this.errorDiscription = errorDiscription;
	}
	public HttpStatus getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(HttpStatus errorCode) {
		this.errorCode = errorCode;
	}
	public ErrorMassege(String errorMassege, String errorDiscription, HttpStatus errorCode) {
		super();
		this.errorMassege = errorMassege;
		this.errorDiscription = errorDiscription;
		this.errorCode = errorCode;
	}
	public ErrorMassege() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
}