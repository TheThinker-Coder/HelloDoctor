package com.hellodoctor.services;

import com.hellodoctor.entities.User;
import com.hellodoctor.requestdto.SignInRequestDto;

public interface UsersService {
	public User  signIn(SignInRequestDto signInRequestDto);

}
