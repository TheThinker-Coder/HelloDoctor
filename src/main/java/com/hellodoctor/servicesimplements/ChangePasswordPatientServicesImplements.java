package com.hellodoctor.servicesimplements;

import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.hellodoctor.constant.Constant;
import com.hellodoctor.entities.Doctor;
import com.hellodoctor.entities.Patient;
import com.hellodoctor.entities.Users;
import com.hellodoctor.exception.EmptyInputException;
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
		String password = null;
		Patient patientEmail = patientRepository.findByPatientEmail(changePasswordDto.getEmail());
		Users findById = usersRepository.findById(patientEmail.getUserId().getUserId()).orElse(null);

		if (patientEmail.getPatientPassword().equals(changePasswordDto.getOldpassword())
				&& changePasswordDto.getNewpassword().equals(changePasswordDto.getConfirmpassword())) {

			if (ObjectUtils.isEmpty(patientEmail)) {
				throw new RecordNotFoundException("current docotor email" + patientEmail);
			} else {
				if (patientEmail != null) {
					patientEmail.setPatientPassword(Base64.getEncoder()
							.encodeToString(changePasswordDto.getConfirmpassword().toString().getBytes()));
					patientRepository.save(patientEmail); // able to save password in doctor
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