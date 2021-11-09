package com.hellodoctor.services;

import com.hellodoctor.responsedto.PatientResponseDto;

public interface UsersService {

	PatientResponseDto getByEmail(String userEmail, String password);
	
	


}
