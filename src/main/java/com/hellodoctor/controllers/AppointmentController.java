package com.hellodoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hellodoctor.exception.EmptyInputException;
import com.hellodoctor.requestdto.AppointmentRequestDto;
import com.hellodoctor.responsedto.AppointmentResponceDto;
import com.hellodoctor.services.AppointmentSerevice;
import com.hellodoctor.services.FileStorageService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {
	
	@Autowired
	private AppointmentSerevice appointmentService;
	
	@Autowired 
	private FileStorageService fileStorageService;
	
	@PostMapping("/saveAppointment")
	public AppointmentResponceDto saveAppointment(@ModelAttribute AppointmentRequestDto appointmentRequestDto, MultipartFile file) {
		String fileAttech = fileStorageService.storeFile(file);
		
		if(ObjectUtils.isEmpty(fileAttech)) {
			throw new EmptyInputException("file is empty");
		}
		appointmentRequestDto.setFileAttech(fileAttech);
		return appointmentService.saveAppointment(appointmentRequestDto);
	}

}
