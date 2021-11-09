package com.hellodoctor.servicesimplements;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hellodoctor.entities.User;
import com.hellodoctor.exception.InvalidDataException;
import com.hellodoctor.repository.UserRepository;
import com.hellodoctor.requestdto.SignInRequestDto;
import com.hellodoctor.responsedto.SignInResponseDto;
import com.hellodoctor.services.SignInService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SignInServicesImplements implements SignInService {

	@Autowired
	private UserRepository usersRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public SignInResponseDto signIn(SignInRequestDto signInRequestDto) {
		log.info("inside SignInServiceImplements");
		SignInResponseDto signInResponseDto = null;
		User users = usersRepository.getUserByuUserName(signInRequestDto.getEmail());// user Reposity
		String password = signInRequestDto.getPassword().trim();
		String p1 = users.getPassword().trim();
		log.info("pasword is " + password);
		log.info("pasword1 is " + p1);
		if (!users.getPassword().equals((Base64.getEncoder().encodeToString(password.toString().getBytes()))))
			throw new InvalidDataException("password not match");

		log.info("repositry called");
		signInResponseDto = new SignInResponseDto(); // dto object
		log.info("Dto object created");
		signInResponseDto.setEmail(users.getEmail());
		signInResponseDto.setMobile(users.getMobile());
		signInResponseDto.setRole(users.getRole());
		signInResponseDto.setUserId(users.getUserId());
		signInResponseDto.setPassword(users.getPassword());
		log.info(users.getPassword());
		log.info(users.getMobile().toString());
		log.info(users.getRole());
		return signInResponseDto;

	}

}
