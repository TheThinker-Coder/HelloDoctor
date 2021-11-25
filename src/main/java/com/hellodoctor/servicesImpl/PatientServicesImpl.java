package com.hellodoctor.servicesImpl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.hellodoctor.constant.Constant;
import com.hellodoctor.entities.Appointment;
import com.hellodoctor.entities.Patient;
import com.hellodoctor.entities.Users;
import com.hellodoctor.exception.AlreadyUseException;
import com.hellodoctor.exception.RecordNotFoundException;
import com.hellodoctor.repository.AppointmentRepository;
import com.hellodoctor.repository.PatientRepository;
import com.hellodoctor.repository.UsersRepository;
import com.hellodoctor.requestdto.PatientRequestDto;
import com.hellodoctor.responsedto.AppointmentResponceDto;
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

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Override
	public PatientResponseDto savePatient(PatientRequestDto patientRequestDto) {

		log.info("start savePatient serviceImpl method");
		Patient existsPatient = patientRepository.findByPatientEmail(patientRequestDto.getPatientEmail());

		if (!ObjectUtils.isEmpty(existsPatient)) {
			throw new AlreadyUseException("try to anther Email");
		}

		Patient patient = new Patient();
		Users user = new Users();

		patient.setPatientName(patientRequestDto.getPatientName());
		patient.setPatientEmail(patientRequestDto.getPatientEmail());
		patient.setPatientMobileNumber(patientRequestDto.getPatientMobileNumber());
		patient.setRegisterDate(new Date());
		patient.setPatientPassword(
				(Base64.getEncoder().encodeToString(patientRequestDto.getPatientPassword().toString().getBytes())));
		patient.setRole(Constant.PATIENTROLE);

		log.info("Persisting patient data into database");
		
		user.setPatientId(patient);
		user.setEmail(patientRequestDto.getPatientEmail());
		user.setPassword(
				(Base64.getEncoder().encodeToString(patientRequestDto.getPatientPassword().toString().getBytes())));
		user.setMobile(patientRequestDto.getPatientMobileNumber());
		user.setRole(patient.getRole());

		Patient savePatient = patientRepository.save(patient);
		log.info("Persisting user data into database");
		usersRipository.save(user);

		PatientResponseDto patientResponseDto = new PatientResponseDto();
		BeanUtils.copyProperties(savePatient, patientResponseDto);
		
		
//		patientResponseDto.setPatientId(savePatient.getPatientId());
//		patientResponseDto.setPatientName(patient.getPatientName());
//		patientResponseDto.setPatientEmail(patient.getPatientEmail());
//		patientResponseDto.setPatientMobileNumber(patient.getPatientMobileNumber());
//		patientResponseDto.setPatientPassword(patient.getPatientPassword());
//		patientResponseDto.setRegisterDate(patient.getRegisterDate());
//		patientResponseDto.setRole(patient.getRole());

		log.info("return savePatient serviceImpl method");
		return patientResponseDto;
	}

	@Override
	public List<PatientResponseDto> getAllPatient() {

		log.info("start getAllPatient serviceImpl method");
		List<Patient> allPatients = patientRepository.findAll();
		List<PatientResponseDto> patientResponseDto = new ArrayList<>();

		for (Patient patient : allPatients) {
			
			PatientResponseDto dto = new PatientResponseDto();
			BeanUtils.copyProperties(patient, dto);
			
//			dto.setPatientId(patient.getPatientId());
//			dto.setPatientName(patient.getPatientName());
//			dto.setPatientEmail(patient.getPatientEmail());
//			dto.setPatientMobileNumber(patient.getPatientMobileNumber());
//			dto.setRole(patient.getRole());
//			dto.setRegisterDate(patient.getRegisterDate());
//			dto.setUpdatedDate(patient.getUpdatedDate());
//			dto.setPatientPassword(patient.getPatientPassword());
			patientResponseDto.add(dto);
			
		}
		

		log.info("return getAllPatient serviceImpl method");
		return patientResponseDto;
	}

	@Override
	public PatientResponseDto getPatientById(Long patientId) {

		log.info("start getPatientById serviceImpl method");
		Patient DbPatient = patientRepository.findById(patientId).orElse(null);

		if (ObjectUtils.isEmpty(DbPatient)) {
			log.error("patientId not found " + patientId);
			throw new RecordNotFoundException("current patient id " + patientId + " not registerd");
		}

		PatientResponseDto patientResponseDto = new PatientResponseDto();
		BeanUtils.copyProperties(DbPatient, patientResponseDto);
		

//		patientResponseDto.setPatientId(DbPatient.getPatientId());
//		patientResponseDto.setPatientName(DbPatient.getPatientName());
//		patientResponseDto.setPatientMobileNumber(DbPatient.getPatientMobileNumber());
//		patientResponseDto.setPatientEmail(DbPatient.getPatientEmail());
//		patientResponseDto.setRegisterDate(DbPatient.getRegisterDate());
//		patientResponseDto.setUpdatedDate(DbPatient.getUpdatedDate());
//		patientResponseDto.setRole(DbPatient.getRole());

		log.info("return getPatientById serviceImpl method");
		return patientResponseDto;
	}

	@Override
	public PatientResponseDto getPatientByPatientEmail(String patientEmail) {

		log.info("start getPatientByPatientEmail serviceImpl method");
		Patient patient = patientRepository.findByPatientEmail(patientEmail);

		if (ObjectUtils.isEmpty(patient)) {

			log.error("patientEmail not found " + patientEmail);
			throw new RecordNotFoundException(patientEmail + " this Email id not found");
		}

		PatientResponseDto patientResponseDto = new PatientResponseDto();
		BeanUtils.copyProperties(patient, patientResponseDto);
		
//		patientResponseDto.setPatientId(patient.getPatientId());
//		patientResponseDto.setPatientName(patient.getPatientName());
//		patientResponseDto.setPatientEmail(patient.getPatientEmail());
//		patientResponseDto.setPatientMobileNumber(patient.getPatientMobileNumber());
//		patientResponseDto.setPatientPassword(patient.getPatientPassword());
//		patientResponseDto.setRole(patient.getRole());
//		patientResponseDto.setRegisterDate(patient.getRegisterDate());
//		patientResponseDto.setUpdatedDate(patient.getUpdatedDate());

		log.info("return getPatientByPatientEmail serviceImpl method");
		return patientResponseDto;
	}

	@Override
	public PatientResponseDto updatePatient(Long patientId, PatientRequestDto patientRequestDto) {

		log.info("start updatePatient serviceImpl method");
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
		dBpatient.setPatientPassword(Base64.getEncoder().encodeToString(patientRequestDto.getPatientPassword()
				.toString().getBytes()));
		dBpatient.setUpdatedDate(new Date());

		Patient updatePatient = patientRepository.save(dBpatient);

		user.setEmail(updatePatient.getPatientEmail());
		user.setMobile(updatePatient.getPatientMobileNumber());
		user.setPassword(updatePatient.getPatientPassword());

		usersRipository.save(user);

		BeanUtils.copyProperties(updatePatient, patientResponseDto);
//		patientResponseDto.setPatientId(updatePatient.getPatientId());
//		patientResponseDto.setPatientEmail(updatePatient.getPatientEmail());
//		patientResponseDto.setPatientName(updatePatient.getPatientName());
//		patientResponseDto.setPatientMobileNumber(updatePatient.getPatientMobileNumber());
//		patientResponseDto.setRole(updatePatient.getRole());
//		patientResponseDto.setRegisterDate(updatePatient.getRegisterDate());
//		patientResponseDto.setUpdatedDate(updatePatient.getUpdatedDate());
//		patientResponseDto.setPatientPassword(updatePatient.getPatientPassword());

		log.info("return updatePatient serviceImpl method");
		return patientResponseDto;
	}

	@Override
	public void deletePatientById(Long patientId) {

		log.info("start deletePatientById serviceImpl method");
		
		Optional<Patient> optional = patientRepository.findById(patientId);
		if(ObjectUtils.isEmpty(optional)) {
			throw new RecordNotFoundException("id not found "+patientId);
		}
		patientRepository.deleteById(patientId);
	}

	@Override
	public List<AppointmentResponceDto> getAppointment(String email) {
		log.info("start getAppointmentByPatientEmail serviceImpl method");
		Patient patient = patientRepository.findByPatientEmail(email);

		if (ObjectUtils.isEmpty(patient)) {
			log.info("getAppointmentByPatientEmail patient not found");
			throw new RecordNotFoundException("patient not found");
		}
		List<AppointmentResponceDto> appointmentResponceDto = new ArrayList<>();

		log.info(" fetching appointment ");
		List<Appointment> appointmentDetails = appointmentRepository.findByPatientEmail(patient.getPatientEmail());
		log.info("getAppointmentByPatientEmail appointment not found");
		if (ObjectUtils.isEmpty(appointmentDetails)) {
			throw new RecordNotFoundException("appointment is empty");
		}
		for (Appointment appDeatiels : appointmentDetails) {
			AppointmentResponceDto dto = new AppointmentResponceDto();
			BeanUtils.copyProperties(appDeatiels, dto);
			
			dto.setDoctorId(appDeatiels.getDoctor().getDoctorId());
			dto.setPatientId(appDeatiels.getPatient().getPatientId());
//			dto.setAppointmentId(appDeatiels.getAppointmentId());
//			dto.setPatientEmail(appDeatiels.getPatientEmail());
//			dto.setPatientName(appDeatiels.getPatientName());
//			dto.setPatientMobileNo(appDeatiels.getPatientMobileNo());
//			dto.setDoctorName(appDeatiels.getDoctorName());
//			dto.setDoctorEmail(appDeatiels.getDoctorEmail());
//			dto.setAppointmentDate(appDeatiels.getAppointmentDate());
//			dto.setTime(appDeatiels.getTime());
//			dto.setFile(appDeatiels.getFile());
			appointmentResponceDto.add(dto);
		}

		log.info(" return getAppointmentByPatientEmail serviceImpl method ");
		return appointmentResponceDto;
	}

	@Override
	public List<Patient> listAll() {
		
		return patientRepository.findAll();
	}
}
