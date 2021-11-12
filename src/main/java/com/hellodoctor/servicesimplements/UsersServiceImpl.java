package com.hellodoctor.servicesimplements;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.hellodoctor.entities.Users;
import com.hellodoctor.exception.InvalidDataException;
import com.hellodoctor.helper.JwtUtil;
import com.hellodoctor.repository.UsersRepository;
import com.hellodoctor.responsedto.JwtResponseDto;
import com.hellodoctor.services.UsersService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UsersRepository usersRepository;

	@Override
	public JwtResponseDto getByEmail(String email, String password) {
		
		log.info("Start getByEmail");
		Users user = usersRepository.findByEmail(email);
		
		if (ObjectUtils.isEmpty(user)) {
			log.error("User not found with email: {}", email);
			throw new InvalidDataException("User not found with email: " + email);
		}

		if (!user.getPassword().equals((Base64.getEncoder().encodeToString(password.toString().getBytes())))) {
			throw new InvalidDataException("password not match");
		}
		
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(user.getEmail());
		log.info("generate token");
		String token = jwtUtil.generateToken(userDetails);
		
		JwtResponseDto dto = new JwtResponseDto();
		dto.setJwtToken(token);
		return dto;
	}

}
