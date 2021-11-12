package com.hellodoctor.services;

import com.hellodoctor.entities.Patient;

public interface PatientForgetPasswordServices {
	
	
public void updateResetPasswordToken(String token, String email) throws Exception;

public Patient getByResetPasswordToken(String token);

public void updatePassword(Patient doctor, String newPassword);

}
