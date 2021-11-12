package com.hellodoctor.servicesimplements;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.hellodoctor.constant.Constant;
import com.hellodoctor.entities.Doctor;
import com.hellodoctor.entities.Users;
import com.hellodoctor.exception.EmptyInputException;
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
		String password = null;
		Doctor doctorEmail = doctorRepository.findBydoctorEmail(changePasswordDto.getEmail());
		Users findById = usersRepository.findById(doctorEmail.getUserId().getUserId()).orElse(null);

		if (doctorEmail.getDoctorPassword().equals(changePasswordDto.getOldpassword())
				&& changePasswordDto.getNewpassword().equals(changePasswordDto.getConfirmpassword())) {

			if (ObjectUtils.isEmpty(doctorEmail)) {
				throw new RecordNotFoundException("current docotor email" + doctorEmail);
			} else {
				if (doctorEmail != null) {
					doctorEmail.setDoctorPassword(Base64.getEncoder()
							.encodeToString(changePasswordDto.getConfirmpassword().toString().getBytes()));
					doctorRepository.save(doctorEmail); // able to save password in doctor
				}
				if (findById != null) {
					// Users users = new Users();
					findById.setPassword(Base64.getEncoder()
							.encodeToString(changePasswordDto.getConfirmpassword().toString().getBytes()));
					usersRepository.save(findById); // able to save password in users
				} else {
					throw new EmptyInputException(Constant.EXCEPTIONEMAILVALIDATION);
				}

			}

		} else {
			throw new RecordNotFoundException(" passwords not matches");
		}

		////
		return true;

	}
}