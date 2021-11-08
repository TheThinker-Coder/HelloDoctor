package com.hellodoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hellodoctor.requestdto.UserRequestDto;
import com.hellodoctor.services.UsersService;


@RestController
public class LoginUserController {
	
	@Autowired
	private UsersService usersService;
	
	@PostMapping("/loginUser")
	public ResponseEntity<?> loginUser(@RequestBody UserRequestDto userRequestDto) {
		usersService.getByEmail(userRequestDto.getEmail(),userRequestDto.getPassword());
		return new ResponseEntity<>("Login success",HttpStatus.OK);
		
	}

}
