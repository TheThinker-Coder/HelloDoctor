package com.hellodoctor.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hellodoctor.constant.Constant;
import com.hellodoctor.entities.HospitalsDetails;
import com.hellodoctor.exception.BusinessException;
import com.hellodoctor.exception.ControllerException;
import com.hellodoctor.requestdto.RequestDto;
import com.hellodoctor.services.HelloDoctorServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/hospital")
public class HospitalController {
	@Autowired
	private HelloDoctorServices helloDoctorServices;
	// add hospital start
	@PostMapping("/addhospital")
	public ResponseEntity<?> addHospital(@RequestBody RequestDto requestDto) {
			log.info("inside hosptial controller");
			this.helloDoctorServices.addHospital(requestDto);
			return new ResponseEntity<RequestDto>(requestDto, HttpStatus.OK);
		}
	// add hospital end
	// get hospital by id
	@GetMapping("/{hospitalId}")
	public ResponseEntity<?> getHospitagetHospitalById(@PathVariable("hospitalId") Long hospitalId) {
		log.info("inside hosptialbyid controller");
		HospitalsDetails hospitalsDetails = helloDoctorServices.getHospitalById(hospitalId);
		return new ResponseEntity<HospitalsDetails>(hospitalsDetails, HttpStatus.OK);
		
//		try {
//			HospitalsDetails hospitalsDetails = helloDoctorServices.getHospitalById(hospitalId);
//			return new ResponseEntity<HospitalsDetails>(hospitalsDetails, HttpStatus.OK);
//		} catch (BusinessException e) {
//			ControllerException ce = new ControllerException();
//			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
//		} catch (Exception e) {
//			ControllerException ce = new ControllerException(Constant.EXCEPTION613 + e.getMessage());
//			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
//		}
	}
	// get hospital by id end

	// get all hospital
	@GetMapping("/allhospital")
	public ResponseEntity<?> getAllHospital() {
		try {
			List<HospitalsDetails> listofAllHospital = helloDoctorServices.getAllHospital();
			return new ResponseEntity<List<HospitalsDetails>>(listofAllHospital, HttpStatus.OK);
		} catch (BusinessException e) {

			ControllerException ce = new ControllerException();
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException(Constant.EXCEPTION613 + e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}

	}
	// get all hospital end
	
	// get hospital by name
	@GetMapping("/hosptailname/{hospitalName}")
	public ResponseEntity<?>findByhospitalName(@PathVariable("hospitalName") String hospitalName){
		log.info("inside hospital controller at findby name hospital");
			HospitalsDetails hospitalsDetailsByName = helloDoctorServices.findByhospitalName(hospitalName);
			return new ResponseEntity<HospitalsDetails>(hospitalsDetailsByName,HttpStatus.OK);
		
	}
}
