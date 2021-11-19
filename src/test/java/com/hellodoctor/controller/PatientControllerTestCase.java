package com.hellodoctor.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellodoctor.entities.Appointment;
import com.hellodoctor.entities.Patient;
import com.hellodoctor.requestdto.AppointmentRequestDto;
import com.hellodoctor.requestdto.PatientRequestDto;
import com.hellodoctor.requestdto.UserRequestDto;
import com.hellodoctor.responsedto.AppointmentResponceDto;
import com.hellodoctor.responsedto.JwtResponseDto;
import com.hellodoctor.responsedto.PatientResponseDto;
import com.hellodoctor.services.PatientService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class PatientControllerTestCase {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PatientService patientService;

	@Autowired
	private ObjectMapper mapper;

	PatientRequestDto patientRequestDto = new PatientRequestDto();
	Patient patient = new Patient();
	PatientResponseDto patientResponseDto = new PatientResponseDto();
	UserRequestDto userRequestDto = new UserRequestDto();
	JwtResponseDto jwtResponseDto = new JwtResponseDto();
	private static String BEARERTOKEN = "Bearer ";

	@BeforeEach
	public void setUp() throws Exception {
		log.info("before each method calling");

		patientRequestDto.setPatientId(1l);
		patientRequestDto.setPatientName("shubham");
		patientRequestDto.setPatientEmail("shubham@gmail.com");
		patientRequestDto.setPatientMobileNumber(1234256789l);
		patientRequestDto.setPatientPassword("123456");
		patientRequestDto.setRegisterDate(new Date());

		userRequestDto.setEmail("rahul@gmail.com");
		userRequestDto.setPassword("123456");

		mapper.registerModule(new ProblemModule());
		mapper.registerModule(new ConstraintViolationProblemModule());

		

	}
	
	@Test
	@Order(1)
	public void testLogin() throws Exception {
		log.info("Login method for calling");
		MvcResult result = mockMvc.perform(post("/loginUser").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(userRequestDto))).andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();

		JwtResponseDto dto = mapper.readValue(response, JwtResponseDto.class);
		BEARERTOKEN = BEARERTOKEN + dto.getJwtToken();
		System.out.print("tokenLogin " + BEARERTOKEN);
	}

	@Test
	public void testSavePatient() throws Exception {
		log.info("save patient method for calling");
		
		BeanUtils.copyProperties(patientRequestDto, patient);
		BeanUtils.copyProperties(patient, patientResponseDto);

		when(patientService.savePatient(patientRequestDto)).thenReturn(patientResponseDto);

		mockMvc.perform(post("/patient/register").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(patientResponseDto))).andExpect(status().isOk());
	}

	@Test
	@Order(2)
	public void testGetAllPatient() throws Exception {
		log.info("get all patient method for calling");

		BeanUtils.copyProperties(patientRequestDto, patient);
		BeanUtils.copyProperties(patient, patientResponseDto);

		List<PatientResponseDto> list = Stream.of(patientResponseDto).collect(Collectors.toList());
		when(patientService.getAllPatient()).thenReturn(list);

		System.out.print("token = " + BEARERTOKEN);
		mockMvc.perform(get("/patient/getAllPatient").header(HttpHeaders.AUTHORIZATION, BEARERTOKEN))
				.andExpect(status().isOk());
	}

	@Test
	@Order(3)
	public void testUpdatePatient() throws Exception {
		log.info("update patient method for calling");

		BeanUtils.copyProperties(patientRequestDto, patient);
		BeanUtils.copyProperties(patient, patientResponseDto);

		when(patientService.updatePatient(1l, patientRequestDto)).thenReturn(patientResponseDto);
		mockMvc.perform(put("/patient/updatePatient/{patientId}", patientRequestDto.getPatientId())
				.contentType(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, BEARERTOKEN)
				.content(mapper.writeValueAsString(patientResponseDto))).andExpect(status().isOk());
	}

	@Test
	@Order(4)
	public void testGetPatientById() throws Exception {
		log.info("get patient by id method for calling");

		BeanUtils.copyProperties(patientRequestDto, patient);
		BeanUtils.copyProperties(patient, patientResponseDto);

		when(patientService.getPatientById(1l)).thenReturn(patientResponseDto);
		mockMvc.perform(get("/patient/getPatientById/{patientId}", patientRequestDto.getPatientId())
				.header(HttpHeaders.AUTHORIZATION, BEARERTOKEN)
				.content(mapper.writeValueAsString(patientResponseDto))).andExpect(status().isOk());
	}

	@Test
	@Order(5)
	public void testGetPatientByEmail() throws Exception {
		log.info("get patient by email method for calling");
		
		BeanUtils.copyProperties(patientRequestDto, patient);
		BeanUtils.copyProperties(patient, patientResponseDto);

		when(patientService.getPatientByPatientEmail(patientRequestDto.getPatientEmail()))
				.thenReturn(patientResponseDto);
		mockMvc.perform(get("/patient/getPatientByEmail/{patientEmail}", patientRequestDto.getPatientEmail())
				.header(HttpHeaders.AUTHORIZATION, BEARERTOKEN)
				.content(mapper.writeValueAsString(patientResponseDto))).andExpect(status().isOk());
	}

	@Test
	@Order(6)
	public void testDeletePatientById() throws Exception {
		log.info("delete patient method for calling");

		doNothing().when(patientService).deletePatientById(patientRequestDto.getPatientId());
		mockMvc.perform(delete("/patient/deletePatientById/{patientId}", patientRequestDto.getPatientId())
				.header(HttpHeaders.AUTHORIZATION, BEARERTOKEN)).andExpect(status().isOk());
	}

	@Test
	@Order(7)
	public void testGetAppointment() throws Exception {
		log.info("get appointment method for calling");

		AppointmentRequestDto appointmentRequestDto = new AppointmentRequestDto();
		appointmentRequestDto.setPatientEmail("shubham@gmail.com");
		appointmentRequestDto.setDoctorEmail("paramh91@gmail.com");
		appointmentRequestDto.setAppointmentDate("30/11/2021");
		appointmentRequestDto.setTime("11:30AM");
		appointmentRequestDto.setFileAttech("report");

		Appointment appointment = new Appointment();
		BeanUtils.copyProperties(appointmentRequestDto, appointment);

		AppointmentResponceDto appointmentResponceDto = new AppointmentResponceDto();
		BeanUtils.copyProperties(appointment, appointmentResponceDto);

		List<AppointmentResponceDto> list = Stream.of(appointmentResponceDto).collect(Collectors.toList());

		when(patientService.getAppointment(patientRequestDto.getPatientEmail())).thenReturn(list);

		mockMvc.perform(get("/patient/getAppointment/{patientEmail}", appointmentRequestDto.getPatientEmail())
				.header(HttpHeaders.AUTHORIZATION, BEARERTOKEN)).andExpect(status().isOk());
	}

}
