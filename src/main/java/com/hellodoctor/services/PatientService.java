package com.hellodoctor.services;

import java.util.List;

import com.hellodoctor.entities.Patient;
import com.hellodoctor.requestdto.PatientRequestDto;
import com.hellodoctor.responsedto.PatientResponseDto;

public interface PatientService {

	public PatientResponseDto savePatient(PatientRequestDto patientRequestDto);
	
	public List<Patient> getAllPatient();

	public PatientResponseDto getPatientById(long patientId);

	public Patient updatePatient(long patientId, PatientRequestDto patientRequestDto);

	public void deletePatientById(long patientId);

	public PatientResponseDto getPatientByEmail(String patientEmail);
}
