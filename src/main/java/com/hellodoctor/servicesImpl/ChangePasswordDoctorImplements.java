package com.hellodoctor.servicesImpl;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.hellodoctor.constant.Constant;
import com.hellodoctor.entities.Doctor;
import com.hellodoctor.entities.Users;
import com.hellodoctor.exception.PasswordException;
import com.hellodoctor.exception.RecordNotFoundException;
import com.hellodoctor.repository.DoctorRepository;
import com.hellodoctor.repository.UsersRepository;
import com.hellodoctor.responsedto.ChangePasswordDto;
import com.hellodoctor.services.ChangePasswordDoctorServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChangePasswordDoctorImplements implements ChangePasswordDoctorServices {
	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private UsersRepository usersRepository;

	@Override
	public Boolean changePassword(ChangePasswordDto changePasswordDto) {
		log.info("Change Password Service Started");
		Boolean password = false;
		Doctor doctorEmail = doctorRepository.findBydoctorEmail(changePasswordDto.getEmail());

		if (!ObjectUtils.isEmpty(doctorEmail)) {
			if (doctorEmail.getDoctorPassword().equals((Base64.getEncoder()
					.encodeToString(changePasswordDto.getOldpassword().toString().getBytes())))) {
				if (changePasswordDto.getNewpassword().equals(changePasswordDto.getConfirmpassword())) {
					doctorEmail.setDoctorPassword(Base64.getEncoder()
							.encodeToString(changePasswordDto.getConfirmpassword().toString().getBytes()));
					doctorRepository.save(doctorEmail); // able to save password in doctor
					password = true;
				} else {
					throw new PasswordException(Constant.NEW_PASSWORD_NOT_MATCH);
				}
			} else {
				throw new PasswordException(Constant.OLD_PASSWORD_NOT_MATCH);
			}
		} else {
			throw new RecordNotFoundException(Constant.INVALID_EMAIL + changePasswordDto.getEmail());
		}
		Users user = usersRepository.findById(doctorEmail.getUserId().getUserId()).orElse(null);

		if (!ObjectUtils.isEmpty(user)) {
			user.setPassword(
					Base64.getEncoder().encodeToString(changePasswordDto.getConfirmpassword().toString().getBytes()));
			usersRepository.save(user); // able to save password in users

		} else {
			throw new RecordNotFoundException(Constant.INVALID_EMAIL + changePasswordDto.getEmail());
		}

		return password;

	}
}