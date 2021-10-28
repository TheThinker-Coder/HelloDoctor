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

import com.hellodoctor.entities.Patient;
import com.hellodoctor.requestdto.PatientRequestDto;
import com.hellodoctor.responsedto.PatientResponseDto;
import com.hellodoctor.services.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	
	@PostMapping("/register")
	public PatientResponseDto savePatient(@RequestBody PatientRequestDto patientRequestDto) {
		
		return patientService.savePatient(patientRequestDto);
		
	}
	
	@GetMapping("/getAllPatient")
	public List<Patient> getAllPatient(){
		return patientService.getAllPatient();
		
	}
	
	@GetMapping("/getPatientById/{patientId}")
	public PatientResponseDto getPatientById(@PathVariable("patientId") long patientId){
		
		return patientService.getPatientById(patientId);
	}
	
	@GetMapping("/getPatientByEmail/{patientEmail}")
	public PatientResponseDto getPatientByEmail(@PathVariable("patientEmail") String patientEmail) {
		return patientService.getPatientByEmail(patientEmail);
		
	}
	
	
	@PutMapping("/updatePatient/{patientId}")
	public Patient updatePatient(@RequestBody PatientRequestDto patientRequestDto, @PathVariable("patientId") long patientId) {
		return patientService.updatePatient(patientId, patientRequestDto);	
	}
	
	@DeleteMapping("/deletePatientById/{patientId}")
		public ResponseEntity<?> deletePatientById(@PathVariable("patientId") long patientId) {
		patientService.deletePatientById(patientId);
		return new ResponseEntity<>("this patient id deleted  "+patientId,HttpStatus.OK);
	}

}
