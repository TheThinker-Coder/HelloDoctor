package com.hellodoctor.exception;

import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomException {
	
	@ExceptionHandler(EmptyInputException.class)
	public ResponseEntity<Object> handleEmptyInputException(EmptyInputException e) {
		ErrorMassege massege = new ErrorMassege("Empty fild is not allowed", e.getMessage(), HttpStatus.BAD_REQUEST);
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(massege);
		return new ResponseEntity<Object>(massege, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<Object> handleInvalidDataException(InvalidDataException e) {
		ErrorMassege massege = new ErrorMassege("Bad Request", e.getMessage(), HttpStatus.BAD_REQUEST);
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(massege);
		return new ResponseEntity<Object>(massege, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RecordNotFoundException.class)
	public ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException e) {
		ErrorMassege massege = new ErrorMassege("Record not found", e.getMessage(), HttpStatus.NOT_FOUND);
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(massege);
		return new ResponseEntity<Object>(massege, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException e) {
		ErrorMassege massege = new ErrorMassege("Record not found", e.getMessage(), HttpStatus.NOT_FOUND);
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(massege);
		return new ResponseEntity<Object>(massege, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> businessException(BusinessException e) {
		ErrorMassege massege = new ErrorMassege("Record not found", e.getMessage(), HttpStatus.NOT_FOUND);
		return new ResponseEntity<Object>(massege, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ControllerException.class)
	public ResponseEntity<Object> controllerException(ControllerException e) {
		ErrorMassege massege = new ErrorMassege("Record not found", e.getMessage(), HttpStatus.NOT_FOUND);
		return new ResponseEntity<Object>(massege, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(FileStorageException.class)
	public ResponseEntity<Object> handleFileStorageException(FileStorageException e) {
		ErrorMassege massege = new ErrorMassege("Could not create the directory", e.getMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Object>(massege, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AlreadyUseException.class)
	public ResponseEntity<Object> handleEmailException(AlreadyUseException e) {
		ErrorMassege massege = new ErrorMassege("Bad Request", e.getMessage(), HttpStatus.BAD_REQUEST);
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(massege);
		return new ResponseEntity<Object>(massege, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(CaptchaInvalidException.class)
	public ResponseEntity<Object> handleCaptchaInvalidException(CaptchaInvalidException e) {
		ErrorMassege massege = new ErrorMassege("this captcha is invalid", e.getMessage(), HttpStatus.BAD_REQUEST);
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(massege);
		return new ResponseEntity<Object>(massege, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(MyFileNotFoundException.class)
	public ResponseEntity<Object> handleMyFileNotFoundException(MyFileNotFoundException e) {
		ErrorMassege massege = new ErrorMassege("not found ", e.getMessage(), HttpStatus.NOT_FOUND);
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(massege);
		return new ResponseEntity<Object>(massege, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(PasswordException.class)
	public ResponseEntity<Object> handlePasswordException(PasswordException e) {
		ErrorMassege massege = new ErrorMassege("Bad Request ", e.getMessage(), HttpStatus.NOT_FOUND);
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(massege);
		return new ResponseEntity<Object>(massege, HttpStatus.BAD_REQUEST);
	}
}
