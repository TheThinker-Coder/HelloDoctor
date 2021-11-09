package com.hellodoctor.servicesimplements;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.hellodoctor.constant.Constant;
import com.hellodoctor.entities.Patient;
import com.hellodoctor.entities.User;
import com.hellodoctor.exception.RecordNotFoundException;
import com.hellodoctor.repository.PatientRepository;
import com.hellodoctor.repository.UserRepository;
import com.hellodoctor.requestdto.PatientRequestDto;
import com.hellodoctor.responsedto.PatientResponseDto;
import com.hellodoctor.services.PatientService;

@Service
public class PatientServicesImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private UserRepository usersRipository;

	@Override
	public PatientResponseDto savePatient(PatientRequestDto patientRequestDto) {

		Patient patient = new Patient();
		User user = new User();

		patient.setPatientName(patientRequestDto.getPatientName());
		patient.setPatientEmail(patientRequestDto.getPatientEmail());
		patient.setPatientMobileNumber(patientRequestDto.getPatientMobileNumber());
		patient.setRegisterDate(new Date());
		patient.setPatientPassword(patientRequestDto.getPatientPassword());
		patient.setRole(Constant.PATIENTROLE);

		Patient savePatient = patientRepository.save(patient);
		user.setPatientId(savePatient);
		user.setEmail(patientRequestDto.getPatientEmail());
		user.setPassword(patientRequestDto.getPatientPassword());
		user.setMobile(patientRequestDto.getPatientMobileNumber());
		user.setRole(patient.getRole());
//		patient.setUserId(user);
		
		usersRipository.save(user);
		
		PatientResponseDto patientResponseDto = new PatientResponseDto();
		patientResponseDto.setPatientId(savePatient.getPatientId());
		patientResponseDto.setPatientName(savePatient.getPatientName());
		patientResponseDto.setPatientEmail(savePatient.getPatientEmail());
		patientResponseDto.setPatientMobileNumber(savePatient.getPatientMobileNumber());
		patientResponseDto.setPatientPassword(savePatient.getPatientPassword());
		patientResponseDto.setRegisterDate(savePatient.getRegisterDate());
		patientResponseDto.setRole(savePatient.getRole());

		return patientResponseDto;
	}

	@Override
	public List<Patient> getAllPatient() {

		List<Patient> allPatients = patientRepository.findAll();

		return allPatients;
	}

	@Override
	public PatientResponseDto getPatientById(long patientId) {

		Patient DbPatient = patientRepository.findById(patientId).orElse(null);

		if (ObjectUtils.isEmpty(DbPatient)) {
			throw new RecordNotFoundException("current patient id " + patientId + " not registerd");
		}

		PatientResponseDto patientResponseDto = new PatientResponseDto();

		patientResponseDto.setPatientId(DbPatient.getPatientId());
		patientResponseDto.setPatientName(DbPatient.getPatientName());
		patientResponseDto.setPatientMobileNumber(DbPatient.getPatientMobileNumber());
		patientResponseDto.setPatientEmail(DbPatient.getPatientEmail());
		patientResponseDto.setRegisterDate(DbPatient.getRegisterDate());
		patientResponseDto.setUpdatedDate(DbPatient.getUpdatedDate());
		patientResponseDto.setRole(DbPatient.getRole());
		return patientResponseDto;
	}

	@Override
	public PatientResponseDto getPatientByEmail(String patientEmail) {

		Patient patient = patientRepository.findByPatientEmail(patientEmail);

		if (ObjectUtils.isEmpty(patient)) {

			throw new RecordNotFoundException(patientEmail + " this Email id not found");
		}

		PatientResponseDto patientResponseDto = new PatientResponseDto();
		patientResponseDto.setPatientId(patient.getPatientId());
		patientResponseDto.setPatientName(patient.getPatientName());
		patientResponseDto.setPatientEmail(patient.getPatientEmail());
		patientResponseDto.setPatientMobileNumber(patient.getPatientMobileNumber());
		patientResponseDto.setPatientPassword(patient.getPatientPassword());
		patientResponseDto.setRole(patient.getRole());
		patientResponseDto.setRegisterDate(patient.getRegisterDate());
		patientResponseDto.setUpdatedDate(patient.getUpdatedDate());

		return patientResponseDto;
	}

	@Override
	public Patient updatePatient(long patientId, PatientRequestDto patientRequestDto) {

		Patient dBpatient = patientRepository.findById(patientId).orElse(null);

		if (ObjectUtils.isEmpty(dBpatient)) {
			throw new RecordNotFoundException("patient not match " + patientId);
		}

		dBpatient.setPatientName(patientRequestDto.getPatientName());
		dBpatient.setPatientMobileNumber(patientRequestDto.getPatientMobileNumber());
		dBpatient.setPatientEmail(patientRequestDto.getPatientEmail());
		dBpatient.setPatientPassword(patientRequestDto.getPatientPassword());
		dBpatient.setUpdatedDate(new Date());

		return patientRepository.save(dBpatient);
	}

	@Override
	public void deletePatientById(long patientId) {

		patientRepository.deleteById(patientId);
	}
}
