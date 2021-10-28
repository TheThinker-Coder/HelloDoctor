package com.hellodoctor.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

//	@Override
//	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
//			HttpStatus status, WebRequest request) {
//		ErrorMassege message = new ErrorMassege("path Mismatch", ex.getMessage(), status);
//		return ResponseEntity.status(status).body(message);
//	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorMassege message = new ErrorMassege("Request method not supported", ex.getMessage(), status);
		return ResponseEntity.status(status).body(message);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorMassege message = new ErrorMassege("media not supported", ex.getMessage(), status);
		return ResponseEntity.status(status).body(message);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorMassege message = new ErrorMassege("Request body is not readable", ex.getMessage(), status);
		return ResponseEntity.status(status).body(message);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ErrorMassege message = new ErrorMassege("path variable is missing", ex.getMessage(), status);
		return ResponseEntity.status(status).body(message);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ErrorMassege message = new ErrorMassege("mismatch of type", ex.getMessage(), status);
		return ResponseEntity.status(status).body(message);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorMassege message = new ErrorMassege("Request param is missing", ex.getMessage(), status);
		return ResponseEntity.status(status).body(message);
	}
}
