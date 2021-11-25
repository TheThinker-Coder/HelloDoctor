package com.hellodoctor.services;

import java.util.List;

import com.hellodoctor.entities.Patient;
import com.hellodoctor.requestdto.PatientRequestDto;
import com.hellodoctor.responsedto.AppointmentResponceDto;
import com.hellodoctor.responsedto.PatientResponseDto;

public interface PatientService {

	public PatientResponseDto savePatient(PatientRequestDto patientRequestDto);
	
	public List<PatientResponseDto> getAllPatient();

	public PatientResponseDto getPatientById(Long patientId);

	public PatientResponseDto updatePatient(Long patientId, PatientRequestDto patientRequestDto);

	public void deletePatientById(Long patientId);

	public PatientResponseDto getPatientByPatientEmail(String patientEmail);
	
	public List<AppointmentResponceDto> getAppointment(String email);

	public List<Patient> listAll();
}
