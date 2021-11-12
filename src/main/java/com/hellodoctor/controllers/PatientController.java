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
import com.hellodoctor.services.PatientService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;

//	save patients api calling
	@PostMapping("/register")
	public PatientResponseDto savePatient(@RequestBody PatientRequestDto patientRequestDto) {
		log.info("calling patient controller savePatient method");
		return patientService.savePatient(patientRequestDto);
	}

//	get all patients api calling
	@GetMapping("/getAllPatient")
	public List<PatientResponseDto> getAllPatient() {
		log.info("calling patient controller getAllPatient method");
		return patientService.getAllPatient();

	}

//	get Patient by patient id api calling
	@GetMapping("/getPatientById/{patientId}")
	public PatientResponseDto getPatientById(@PathVariable("patientId") Long patientId) {
		log.info("calling patient controller getPatientById method");
		return patientService.getPatientById(patientId);
	}

//	get patient by patient email api calling
	@GetMapping("/getPatientByEmail/{patientEmail}")
	public PatientResponseDto getPatientByEmail(@PathVariable("patientEmail") String patientEmail) {
		log.info("calling patient controller getPatientByEmail method");
		return patientService.getPatientByPatientEmail(patientEmail);

	}

//	update patient by patient id api calling
	@PutMapping("/updatePatient/{patientId}")
	public PatientResponseDto updatePatient(@RequestBody PatientRequestDto patientRequestDto,
			@PathVariable("patientId") long patientId) {
		log.info("calling patient controller updatePatient method");
		return patientService.updatePatient(patientId, patientRequestDto);
	}

//	patient delete by patient id api calling
	@DeleteMapping("/deletePatientById/{patientId}")
	public ResponseEntity<?> deletePatientById(@PathVariable("patientId") Long patientId) {
		log.info("calling patient controller deletePatientById method");
		patientService.deletePatientById(patientId);
		return new ResponseEntity<>("this patient id deleted  " + patientId, HttpStatus.OK);
	}

//	get appointment by patient email api calling
	@GetMapping("/getAppointment/{patientEmail}")
	public List<AppointmentResponceDto> getAppointment(@PathVariable("patientEmail") String patientEmail) {
		log.info("calling patient controller getAppointment method");
		return patientService.getAppointment(patientEmail);

	}

}
