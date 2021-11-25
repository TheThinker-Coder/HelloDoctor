package com.hellodoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hellodoctor.constant.Constant;
import com.hellodoctor.responsedto.ChangePasswordDto;
import com.hellodoctor.services.ChangePasswordPatientServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/patient")
public class ChangePasswordPatient {
	@Autowired
	private ChangePasswordPatientServices changePasswordServices;
	@PostMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
		log.info("inside change password");
		Boolean password = this.changePasswordServices.changePassword(changePasswordDto);
		String pass=null;
		if(password!=true) {
			password = false;
			pass=Constant.PASSWORD_NOT_UPDATE;
		}
		pass = Constant.PASSWORD_UPDATE;
		return new ResponseEntity<String>(pass, HttpStatus.OK);
	}

}