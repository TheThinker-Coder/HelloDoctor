package com.hellodoctor.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

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
import com.hellodoctor.entities.Doctor;
import com.hellodoctor.requestdto.RequestDto;
import com.hellodoctor.requestdto.UserRequestDto;
import com.hellodoctor.responsedto.CaptchaResponse;
import com.hellodoctor.responsedto.JwtResponseDto;
import com.hellodoctor.services.HelloDoctorServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation.class)
public class DoctorControllerTestCase {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private HelloDoctorServices doctorService;

	@Autowired
	private ObjectMapper mapper;

	RequestDto requestDto = new RequestDto();
	Doctor doctor = new Doctor();
	UserRequestDto userRequestDto = new UserRequestDto();
	JwtResponseDto jwtResponseDto = new JwtResponseDto();
	private static String BEARER_TOKEN = "Bearer ";
	private static Long captchaId;

	@BeforeEach
	public void setup() {

		userRequestDto.setEmail("hemantjain@gmail.com");
		userRequestDto.setPassword("123456");

		requestDto.setDoctorId(1l);
		requestDto.setDoctorName("Hemant Jain");
		requestDto.setDoctorEmail("hemantjain@gmail.com");
		requestDto.setDoctorMobileNumber(2103546987l);
		requestDto.setDoctorGender("female");
		requestDto.setRegisterDate(new Date());
		requestDto.setDoctorSpecilzation("Surgeon");
		requestDto.setHospitalName("lifecare");
		requestDto.setRole("ROLE_DOCTORE");

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
		log.info("login test case start");
		userRequestDto.setCaptchaId(captchaId);
		MvcResult result = mockMvc.perform(post("/loginUser").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(userRequestDto))).andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();

		System.out.println("response " + response);

		JwtResponseDto dto = mapper.readValue(response, JwtResponseDto.class);
		System.out.println("-------------------------------------------------");
		System.out.println("JWT token " + dto.getJwtToken());
		System.out.println("--------------------------------------------------");
		BEARER_TOKEN = BEARER_TOKEN + dto.getJwtToken();
		log.info("login tast case end");
	}

	@Test
	@Order(3)
	public void testSaveDoctor() throws Exception {
		log.info("save doctor test case start");
		BeanUtils.copyProperties(requestDto, doctor);

		when(doctorService.addDoctor(requestDto)).thenReturn(doctor);
		mockMvc.perform(post("/doctor/adddoctor").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(requestDto))).andExpect(status().isOk());
		log.info("save doctor test case end ");
	}

	@Test
	@Order(4)
	public void testGetByDoctorId() throws Exception {
		log.info("get doctor by doctor id test case start");
		BeanUtils.copyProperties(requestDto, doctor);

		when(doctorService.getDoctorById(1l)).thenReturn(doctor);
		mockMvc.perform(get("/doctor/{doctorId}", requestDto.getDoctorId())
				.header(HttpHeaders.AUTHORIZATION, BEARER_TOKEN).content(mapper.writeValueAsString(requestDto)))
				.andExpect(status().isOk());
		log.info("get doctor by doctor id test case end");
	}

}
