package com.hellodoctor.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;

import com.hellodoctor.entities.Patient;
import com.hellodoctor.entities.Users;
import com.hellodoctor.repository.PatientRepository;
import com.hellodoctor.repository.UsersRepository;
import com.hellodoctor.requestdto.PatientRequestDto;
import com.hellodoctor.responsedto.PatientResponseDto;
import com.hellodoctor.servicesImpl.PatientServicesImpl;

@SpringBootTest
public class PatientServiceTestCase {

	@InjectMocks
	private PatientServicesImpl patientService;

	@Mock
	private PatientRepository patientRepository;

	@Mock
	private UsersRepository userRepository;

	PatientRequestDto patientRequestDto = null;

	@BeforeEach
	public void setUp() {
		patientRequestDto = new PatientRequestDto();
		patientRequestDto.setPatientId(1l);
		patientRequestDto.setPatientName("shubham");
		patientRequestDto.setPatientEmail("shubham@gmail.com");
		patientRequestDto.setPatientMobileNumber(1234256789l);
		patientRequestDto.setPatientPassword("123456");
		patientRequestDto.setRegisterDate(new Date());
	}

//	@Test
//	public void testRegisterPatient() {
//
//		Patient patient = new Patient();
//		Users user = new Users();
//
//		BeanUtils.copyProperties(patientRequestDto, patient);
//		patient.setRole("ROLE_PATIENT");
//
//		when(patientRepository.save(patient)).thenReturn(patient);
//
//		user.setEmail(patientRequestDto.getPatientEmail());
//		user.setMobile(patientRequestDto.getPatientMobileNumber());
//		user.setPassword(patientRequestDto.getPatientPassword());
//
//		userRepository.save(user);
//		assertThat(patientService.savePatient(patientRequestDto).getPatientEmail()).isNotNull();
//
//	}

	@Test
	public void testGetPatientById() {

		Patient patient = new Patient();
		BeanUtils.copyProperties(patientRequestDto, patient);
		patient.setRole("ROLE_PATIENT");

		when(patientRepository.findById(patient.getPatientId())).thenReturn(Optional.of(patient));

		assertThat(patientService.getPatientById(patient.getPatientId())).isNotNull();

	}

	@Test
	public void testGetAllPatient() {

		Patient patient = new Patient();
		BeanUtils.copyProperties(patientRequestDto, patient);
		patient.setRole("ROLE_PATIENT");

		when(patientRepository.findAll()).thenReturn(Stream.of(patient).collect(Collectors.toList()));
		assertEquals(1, patientService.getAllPatient().size());

	}

//	@Test
//	public void testUpdatePatient() {
//
//		Patient patient = new Patient();
//		BeanUtils.copyProperties(patientRequestDto, patient);
//		patient.setRole("ROLE_PATIENT");
//
//		when(patientRepository.save(patient)).thenReturn(patient);
//
//		PatientResponseDto updatePatient = patientService.updatePatient(1l, patientRequestDto);
//
//		assertThat(updatePatient).isNotNull();
//
//	}

//	@Test
//	public void testDeletePatient() {
//
//		Patient patient = new Patient();
//		BeanUtils.copyProperties(patientRequestDto, patient);
//		patient.setRole("ROLE_PATIENT");
//
//		when(patientRepository.save(patient)).thenReturn(patient);
//
//		patientService.deletePatientById(1l);
//		verify(patientRepository, times(1)).deleteById(1l);
//	}
}
