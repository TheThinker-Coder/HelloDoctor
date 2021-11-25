package com.hellodoctor.servicesImpl;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.hellodoctor.constant.Constant;
import com.hellodoctor.entities.Patient;
import com.hellodoctor.entities.Users;
import com.hellodoctor.exception.PasswordException;
import com.hellodoctor.exception.RecordNotFoundException;
import com.hellodoctor.repository.PatientRepository;
import com.hellodoctor.repository.UsersRepository;
import com.hellodoctor.responsedto.ChangePasswordDto;
import com.hellodoctor.services.ChangePasswordPatientServices;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ChangePasswordPatientServicesImplements implements ChangePasswordPatientServices {
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private UsersRepository usersRepository;

	public Boolean changePassword(ChangePasswordDto changePasswordDto) {
		log.info("Change Password Service Started");
		Boolean password = false;
		Patient patient = patientRepository.findByPatientEmail(changePasswordDto.getEmail());

		if (!ObjectUtils.isEmpty(patient)) {
			if (patient.getPatientPassword().equals(
					(Base64.getEncoder().encodeToString(changePasswordDto.getOldpassword().toString().getBytes())))) {
				if (changePasswordDto.getNewpassword().equals(changePasswordDto.getConfirmpassword())) {
					patient.setPatientPassword(Base64.getEncoder()
							.encodeToString(changePasswordDto.getConfirmpassword().toString().getBytes()));
					patientRepository.save(patient); // able to save password in patient
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
		Users user = usersRepository.findById(patient.getUserId().getUserId()).orElse(null);

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