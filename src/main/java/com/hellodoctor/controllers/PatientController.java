package com.hellodoctor.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hellodoctor.requestdto.PatientRequestDto;
import com.hellodoctor.responsedto.AppointmentResponceDto;
import com.hellodoctor.responsedto.PatientResponseDto;
import com.hellodoctor.services.AppointmentSerevice;
import com.hellodoctor.services.PatientService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@Autowired
	private AppointmentSerevice appointmentService;

	@PostMapping("/register")
	public PatientResponseDto savePatient(@RequestBody PatientRequestDto patientRequestDto) {
		log.info("controller savePatient method");
		return patientService.savePatient(patientRequestDto);

	}

	@GetMapping("/getAllPatient")
	public List<PatientResponseDto> getAllPatient() {

		log.info("controller getAllPatient method");
		return patientService.getAllPatient();

	}

	@GetMapping("/getPatientById/{patientId}")
	public PatientResponseDto getPatientById(@PathVariable("patientId") Long patientId) {
		log.info("controller getPatientById");
		return patientService.getPatientById(patientId);
	}

	@GetMapping("/getPatientByEmail/{patientEmail}")
	public PatientResponseDto getPatientByEmail(@PathVariable("patientEmail") String patientEmail) {
		log.info("controller getPatientByEmail");
		return patientService.getPatientByPatientEmail(patientEmail);

	}

	@PutMapping("/updatePatient/{patientId}")
	public PatientResponseDto updatePatient(@RequestBody PatientRequestDto patientRequestDto,
			@PathVariable("patientId") long patientId) {
		log.info("controller updatePatient");
		return patientService.updatePatient(patientId, patientRequestDto);
	}

	@DeleteMapping("/deletePatientById/{patientId}")
	public ResponseEntity<?> deletePatientById(@PathVariable("patientId") Long patientId) {
		log.info("controller deletePatientById");
		patientService.deletePatientById(patientId);
		return new ResponseEntity<>("this patient id deleted  " + patientId, HttpStatus.OK);
	}

	@GetMapping("/getAppointment/{byPatientEmail}")
	public List<AppointmentResponceDto> getAppointment(@PathVariable("byPatientEmail") String byPatientEmail) {
		return appointmentService.getAppointmentByPatientEmail(byPatientEmail);

	}

}
