package com.hellodoctor.services;

import com.hellodoctor.requestdto.SignInRequestDto;
import com.hellodoctor.responsedto.SignInResponseDto;

public interface SignInService {
	
	public SignInResponseDto signIn(SignInRequestDto signInRequestDto);

}
