package com.hellodoctor.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hellodoctor.constant.Constant;
import com.hellodoctor.exception.EmptyInputException;
import com.hellodoctor.requestdto.AppointmentRequestDto;
import com.hellodoctor.responsedto.AppointmentResponceDto;
import com.hellodoctor.services.AppointmentService;
import com.hellodoctor.services.FileStorageService;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private FileStorageService fileStorageService;

	@PostMapping("/saveAppointment")
	public AppointmentResponceDto saveAppointment(@ModelAttribute AppointmentRequestDto appointmentRequestDto,
			MultipartFile file) {
		String fileAttech = fileStorageService.storeFile(file);

		if (ObjectUtils.isEmpty(fileAttech)) {
			throw new EmptyInputException(Constant.EMPTY_FILE);
		}
		appointmentRequestDto.setFileAttech(fileAttech);
		return appointmentService.saveAppointment(appointmentRequestDto);
	}

	@GetMapping("/getAppointment/{byPatientEmail}")
	public List<AppointmentResponceDto> getAppointment(@PathVariable("byPatientEmail") String byPatientEmail) {
		return appointmentService.getAppointmentByPatientEmail(byPatientEmail);

	}
	
	@GetMapping( "/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		Resource resource = fileStorageService.loadFileAsResource(fileName);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		if (contentType == null) {
			contentType = Constant.DEFAULT_CONTENT_TYPE;
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION,
						String.format(Constant.FILE_DOWNLOAD_HTTP_HEADER, resource.getFilename()))
				.body(resource);
	}


}
