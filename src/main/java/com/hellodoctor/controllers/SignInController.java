package com.hellodoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hellodoctor.configuration.MyConfig;
import com.hellodoctor.helper.JwtUtil;
import com.hellodoctor.requestdto.JwtRequest;
import com.hellodoctor.requestdto.SignInRequestDto;
import com.hellodoctor.responsedto.JwtResponse;
import com.hellodoctor.responsedto.SignInResponseDto;
import com.hellodoctor.services.SignInService;

import com.hellodoctor.servicesimplements.UserDetailsServiceImplements;

@RestController
public class SignInController {
	@Autowired
	private SignInService signInService;

	

	@GetMapping("/welcome")
	public String welcome() {

		return "welcome to the page";
	}

	@PostMapping("/doctorLogin")
	public  SignInResponseDto signIn(@RequestBody SignInRequestDto signInRequestDto) {
		return this.signInService.signIn(signInRequestDto);
		
	}

	

}
