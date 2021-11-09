package com.hellodoctor.services;

import com.hellodoctor.entities.Users;
import com.hellodoctor.requestdto.SignInRequestDto;

public interface UsersService {
	
	public Users  signIn(SignInRequestDto signInRequestDto);

}
