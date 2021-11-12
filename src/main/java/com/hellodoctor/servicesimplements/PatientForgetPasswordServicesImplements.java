package com.hellodoctor.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hellodoctor.entities.Patient;
import com.hellodoctor.entities.Users;
import com.hellodoctor.exception.RecordNotFoundException;
import com.hellodoctor.repository.PatientRepository;
import com.hellodoctor.repository.UsersRepository;
import com.hellodoctor.services.PatientForgetPasswordServices;
@Service
public class PatientForgetPasswordServicesImplements implements PatientForgetPasswordServices {
	
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private UsersRepository usersRepository;
	@Override
	public void updateResetPasswordToken(String token, String email) throws RecordNotFoundException {
		System.out.println(email);
		Patient user = patientRepository.findByPatientEmail(email);
		if (user != null) {
			user.setResetPasswordToken(token);
			patientRepository.save(user);
		} else {
			throw new RecordNotFoundException("Cant find the user with this email" + email);
		}
	}

	@Override
	public Patient getByResetPasswordToken(String token) {
		return patientRepository.findByResetPasswordToken(token);
	}

	@Override
	public void updatePassword(Patient user, String newPassword) {
		user.setPatientPassword(newPassword);
		Users findById = usersRepository.findById(user.getUserId().getUserId()).orElse(null);
		user.setResetPasswordToken(null);
		patientRepository.save(user);
		if (findById != null) {
			// Users users = new Users();
			findById.setPassword(user.getPatientPassword());
			usersRepository.save(findById);

	}
	
	
	}
}
