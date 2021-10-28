//package com.hellodoctor.services;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//import java.util.Date;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import com.hellodoctor.entities.Patient;
//import com.hellodoctor.repository.PatientRepository;
//import com.hellodoctor.requestdto.PatientRequestDto;
//
//@SpringBootTest
//public class PatientTest {
//	
//	@Autowired
//	private PatientService patientService;
//	
//	@MockBean
//	private PatientRepository patientRepository;
//	
//	
//	@Test
//	public void testRegisterPatient() {
//		
//		Patient patient = new Patient();
//		PatientRequestDto patientRequestDto = new PatientRequestDto();
////		patientRequestDto.setPatientId(1l);
//		patientRequestDto.setPatientName("shubham");
//		patientRequestDto.setPatientEmail("shubham@gmail.com");
//		patientRequestDto.setPatientMobileNumber(1234256789l);
//		patientRequestDto.setPatientPassword("123456");
//		patientRequestDto.setRegisterDate(new Date());
//		
//		patient.setPatientId(patientRequestDto.getPatientId());
//		patient.setPatientName(patientRequestDto.getPatientName());
//		patient.setPatientEmail(patientRequestDto.getPatientEmail());
//		patient.setPatientMobileNumber(patientRequestDto.getPatientMobileNumber());
//		patient.setPatientPassword(patientRequestDto.getPatientPassword());
//		
//		
//		when(patientRepository.save(patient)).thenReturn(patient);
//		
//		//Patient savePatient = patientService.savePatient(patientRequestDto);
//		assertEquals(patient, patientService.savePatient(patientRequestDto));
//		
//	}
//	
//	@Test
//	public void testGetPatientById() {
//		
//		Patient patient = new Patient();
//		PatientRequestDto patientRequestDto = new PatientRequestDto();
//		patientRequestDto.setPatientId(1l);
//		patientRequestDto.setPatientName("shubham");
//		patientRequestDto.setPatientEmail("shubham@gmail.com");
//		patientRequestDto.setPatientMobileNumber(1234256789l);
//		patientRequestDto.setPatientPassword("123456");
//		patientRequestDto.setRegisterDate(new Date());
//		
//		patient.setPatientId(patientRequestDto.getPatientId());
//		patient.setPatientName(patientRequestDto.getPatientName());
//		patient.setPatientEmail(patientRequestDto.getPatientEmail());
//		patient.setPatientMobileNumber(patientRequestDto.getPatientMobileNumber());
//		patient.setPatientPassword(patientRequestDto.getPatientPassword());
//		
//		when(patientRepository.findById(patient.getPatientId())).thenReturn(null);
//		
//	}
//
//}
