package com.hellodoctor.servicesimplements;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.hellodoctor.constant.Constant;
import com.hellodoctor.entities.Patient;
import com.hellodoctor.entities.Users;
import com.hellodoctor.exception.RecordNotFoundException;
import com.hellodoctor.repository.PatientRepository;
import com.hellodoctor.repository.UsersRepository;
import com.hellodoctor.requestdto.PatientRequestDto;
import com.hellodoctor.responsedto.PatientResponseDto;
import com.hellodoctor.services.PatientService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PatientServicesImpl implements PatientService {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private UsersRepository usersRipository;

	
	@Override
	public PatientResponseDto savePatient(PatientRequestDto patientRequestDto) {

		Patient patient = new Patient();
		Users user = new Users();

		patient.setPatientName(patientRequestDto.getPatientName());
		patient.setPatientEmail(patientRequestDto.getPatientEmail());
		patient.setPatientMobileNumber(patientRequestDto.getPatientMobileNumber());
		patient.setRegisterDate(new Date());
		patient.setPatientPassword((Base64.getEncoder().encodeToString(patientRequestDto.getPatientPassword().toString().getBytes())));
		patient.setRole(Constant.PATIENTROLE);

		log.info("Persisting patient data into database");
		Patient savePatient = patientRepository.save(patient);
		user.setPatientId(savePatient);
		user.setEmail(patientRequestDto.getPatientEmail());
		user.setPassword((Base64.getEncoder().encodeToString(patientRequestDto.getPatientPassword().toString().getBytes())));
		user.setMobile(patientRequestDto.getPatientMobileNumber());
		user.setRole(patient.getRole());

		log.info("Persisting user data into database");
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
	public List<PatientResponseDto> getAllPatient() {

		List<Patient> allPatients = patientRepository.findAll();
		List<PatientResponseDto> patientResponseDto = new ArrayList<>();

		for (Patient patient : allPatients) {
			PatientResponseDto dto = new PatientResponseDto();
			dto.setPatientId(patient.getPatientId());
			dto.setPatientName(patient.getPatientName());
			dto.setPatientEmail(patient.getPatientEmail());
			dto.setPatientMobileNumber(patient.getPatientMobileNumber());
			dto.setRole(patient.getRole());
			dto.setRegisterDate(patient.getRegisterDate());
			dto.setUpdatedDate(patient.getUpdatedDate());
			dto.setPatientPassword(patient.getPatientPassword());
			patientResponseDto.add(dto);
		}

		return patientResponseDto;
	}

	@Override
	public PatientResponseDto getPatientById(Long patientId) {

		Patient DbPatient = patientRepository.findById(patientId).orElse(null);

		if (ObjectUtils.isEmpty(DbPatient)) {
			log.error("patientId not found " + patientId);
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
	public PatientResponseDto getPatientByPatientEmail(String patientEmail) {

		Patient patient = patientRepository.findByPatientEmail(patientEmail);

		if (ObjectUtils.isEmpty(patient)) {

			log.error("patientEmail not found " + patientEmail);
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
	public PatientResponseDto updatePatient(Long patientId, PatientRequestDto patientRequestDto) {

		Patient dBpatient = patientRepository.findById(patientId).orElse(null);

		if (ObjectUtils.isEmpty(dBpatient)) {
			log.error("patientId not found " + patientId);
			throw new RecordNotFoundException("patient not match " + patientId);
		}

		Users user = usersRipository.findById(dBpatient.getUserId().getUserId()).orElse(null);

		if (ObjectUtils.isEmpty(user)) {
			log.error("patientId not found ");
			throw new RecordNotFoundException("user not match " + user);
		}

		PatientResponseDto patientResponseDto = new PatientResponseDto();

		dBpatient.setPatientName(patientRequestDto.getPatientName());
		dBpatient.setPatientMobileNumber(patientRequestDto.getPatientMobileNumber());
		dBpatient.setPatientEmail(patientRequestDto.getPatientEmail());
		dBpatient.setPatientPassword(patientRequestDto.getPatientPassword());
		dBpatient.setUpdatedDate(new Date());

		Patient updatePatient = patientRepository.save(dBpatient);

		user.setEmail(updatePatient.getPatientEmail());
		user.setMobile(updatePatient.getPatientMobileNumber());
		user.setPassword(updatePatient.getPatientPassword());

		usersRipository.save(user);

		patientResponseDto.setPatientId(updatePatient.getPatientId());
		patientResponseDto.setPatientEmail(updatePatient.getPatientEmail());
		patientResponseDto.setPatientName(updatePatient.getPatientName());
		patientResponseDto.setPatientMobileNumber(updatePatient.getPatientMobileNumber());
		patientResponseDto.setRole(updatePatient.getRole());
		patientResponseDto.setRegisterDate(updatePatient.getRegisterDate());
		patientResponseDto.setUpdatedDate(updatePatient.getUpdatedDate());
		patientResponseDto.setPatientPassword(updatePatient.getPatientPassword());

		return patientResponseDto;
	}

	@Override
	public void deletePatientById(Long patientId) {

		patientRepository.deleteById(patientId);
	}
}
