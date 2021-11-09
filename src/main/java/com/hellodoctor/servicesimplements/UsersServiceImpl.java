package com.hellodoctor.servicesimplements;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.hellodoctor.entities.Users;
import com.hellodoctor.exception.InvalidDataException;
import com.hellodoctor.repository.UsersRepository;
import com.hellodoctor.responsedto.PatientResponseDto;
import com.hellodoctor.services.UsersService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersRepository usersRepository;

	@Override
	public PatientResponseDto getByEmail(String email, String password) {
		Users user = usersRepository.findByEmail(email);
		if (ObjectUtils.isEmpty(user)) {
			log.error("User not found with email: {}", email);
			throw new UsernameNotFoundException("User not found with email: " + email);
		}

		if (!user.getPassword().equals((Base64.getEncoder().encodeToString(password.toString().getBytes())))) {
			throw new InvalidDataException("password not match");
		}
		PatientResponseDto dto = new PatientResponseDto();
		dto.setPatientId(user.getUserId());
		dto.setPatientEmail(user.getEmail());
		dto.setRole(user.getRole());

		return dto;
	}

}
