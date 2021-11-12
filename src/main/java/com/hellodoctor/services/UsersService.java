package com.hellodoctor.services;

import com.hellodoctor.responsedto.JwtResponseDto;

public interface UsersService {

	JwtResponseDto getByEmail(String userEmail, String password);
	
	


}
