package com.hellodoctor.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellodoctor.entities.Appointment;
import com.hellodoctor.requestdto.AppointmentRequestDto;
import com.hellodoctor.requestdto.UserRequestDto;
import com.hellodoctor.responsedto.AppointmentResponceDto;
import com.hellodoctor.responsedto.CaptchaResponse;
import com.hellodoctor.responsedto.JwtResponseDto;
import com.hellodoctor.services.AppointmentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class AppointmentControllerTestCase {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AppointmentService appointmentService;

	private static String BEARERTOKEN = "Bearer ";
	private static Long captchaId;
	@Autowired
	private ObjectMapper mapper;

	AppointmentRequestDto requestDto = new AppointmentRequestDto();
	Appointment appointment = new Appointment();
	AppointmentResponceDto responceDto = new AppointmentResponceDto();

	UserRequestDto userRequestDto = new UserRequestDto();
	JwtResponseDto jwtResponseDto = new JwtResponseDto();

	@BeforeEach
	public void setUp() {
		requestDto.setPatientEmail("rahul@gmail.com");
		requestDto.setDoctorEmail("hemantjain@gmail.com");
		requestDto.setAppointmentDate("01/12/2021");
		requestDto.setTime("03:00AM");
		requestDto.setFileAttech("report");

		userRequestDto.setEmail("rahul@gmail.com");
		userRequestDto.setPassword("123456");

		mapper.registerModule(new ProblemModule());
		mapper.registerModule(new ConstraintViolationProblemModule());
	}
	
	@Test
	@Order(1)
	public void testCreateCaptcha() throws Exception {

		MvcResult result = mockMvc.perform(get("/createCaptcha").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();

		System.out.println("result " + response);
		CaptchaResponse captchaResponse = mapper.readValue(response, CaptchaResponse.class);
		captchaId = captchaResponse.getCaptchaId();
		System.out.println("captchaId " + captchaId);

	}

	@Test
	@Order(2)
	public void testLogin() throws Exception {
		log.info("Login method for calling");
		userRequestDto.setCaptchaId(captchaId);
		MvcResult result = mockMvc.perform(post("/loginUser").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(userRequestDto))).andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();

		JwtResponseDto dto = mapper.readValue(response, JwtResponseDto.class);
		BEARERTOKEN = BEARERTOKEN + dto.getJwtToken();
		System.out.print("tokenLogin " + BEARERTOKEN);
	}

//	@Test
//	@Order(2)
//	public void testSaveAppointment() throws Exception {
//
//		BeanUtils.copyProperties(requestDto, appointment);
//		BeanUtils.copyProperties(appointment, responceDto);
//
//		when(appointmentService.saveAppointment(requestDto)).thenReturn(responceDto);
//		mockMvc.perform(post("/appointment/saveAppointment").contentType(MediaType.APPLICATION_JSON)
//				.content(mapper.writeValueAsString(responceDto))).andExpect(status().isOk());
//	}

	@Test
	@Order(3)
	public void testGetAppointment() throws Exception {

		BeanUtils.copyProperties(requestDto, appointment);
		BeanUtils.copyProperties(appointment, responceDto);

		List<AppointmentResponceDto> list = Stream.of(responceDto).collect(Collectors.toList());
		System.out.println(list);
		when(appointmentService.getAppointmentByPatientEmail(requestDto.getPatientEmail())).thenReturn(list);
		mockMvc.perform(get("/appointment/getAppointment/{byPatientEmail}", requestDto.getPatientEmail()))
				.andExpect(status().isOk());
	}

}
