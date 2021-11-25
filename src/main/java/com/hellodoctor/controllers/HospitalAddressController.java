package com.hellodoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hellodoctor.constant.Constant;
import com.hellodoctor.entities.HospitalAddress;
import com.hellodoctor.exception.BusinessException;
import com.hellodoctor.exception.ControllerException;
import com.hellodoctor.services.HelloDoctorServices;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/hospitaladdress")
public class HospitalAddressController {
	@Autowired
	private HelloDoctorServices helloDoctorServices;
	
	// get address by id
	@GetMapping("/{addressId}")
	public  ResponseEntity<?> getHospitalAddressById(@PathVariable("addressId") Long addressId) {
		log.info("inside hosptialaddressbyid controller");
			HospitalAddress hospitalAddress = helloDoctorServices.getHospitalAddressById(addressId);
			return new ResponseEntity<HospitalAddress>(hospitalAddress, HttpStatus.OK);
	}
	// get address by id end 

}
