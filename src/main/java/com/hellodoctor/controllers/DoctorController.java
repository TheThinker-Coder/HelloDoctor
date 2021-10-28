package com.hellodoctor.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hellodoctor.constant.Constant;
import com.hellodoctor.entities.Doctor;
import com.hellodoctor.exception.BusinessException;
import com.hellodoctor.exception.ControllerException;
import com.hellodoctor.requestdto.RequestDto;
import com.hellodoctor.services.HelloDoctorServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/doctor")
public class DoctorController {
	@Autowired
	private HelloDoctorServices helloDoctorServices;

	@GetMapping("/")
	public ResponseEntity<String> home() {
		log.info("inside controller on home");
		return new ResponseEntity<>(Constant.WELCOME, HttpStatus.OK);
	}

	// doctor post method
	@PostMapping("/adddoctor")
	public ResponseEntity<?> addDoctor(@RequestBody RequestDto requestDto) {
		try {
			log.info("inside controller on doctor save");
			this.helloDoctorServices.addDoctor(requestDto);
			return new ResponseEntity<RequestDto>(requestDto, HttpStatus.OK);
		} catch (BusinessException e) {
			log.trace("InsideEmployeePostCatchBusinessBlock");
			log.trace("ControllerExceptionStarted");
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			log.error("ControllerExceptionError");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.trace("InsideEmployeePostExceptionBlock");
			log.trace("ExceptionStarted");
			ControllerException ce = new ControllerException("613", Constant.EXCEPTION613 + e.getMessage());
			log.error("ControllerExceptionError");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

	// doctor post method end
	// get doctor by id
	@GetMapping("/{doctorId}")
	public ResponseEntity<?> getDoctorById(@PathVariable("doctorId") Long getDoctorById) {
		log.info("inside doctor byid controller");
		try {
			Doctor doctorById = helloDoctorServices.getDoctorById(getDoctorById);
			return new ResponseEntity<Doctor>(doctorById, HttpStatus.OK);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException(Constant.EXCEPTIONCODE608,
					Constant.EXCEPTION613 + e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

	// get doctor by id end
	// get doctor by email
	@GetMapping("/doctoremail/{doctorEmail}")
	public ResponseEntity<?> findBydoctorEmail(@PathVariable("doctorEmail") String doctorEmail) {
		log.info("inside doctorby email controller");
		try {
			Doctor doctorByEmail = helloDoctorServices.findBydoctorEmail(doctorEmail);
			return new ResponseEntity<Doctor>(doctorByEmail, HttpStatus.OK);
		} catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException(Constant.EXCEPTIONCODE608,
					Constant.EXCEPTION613 + e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}

	}

	// get doctor by email end
	// get all doctor
	@GetMapping("/alldoctor")
	public ResponseEntity<?> getAllDoctor() {
		try {
			List<Doctor> listofAllDoctor = helloDoctorServices.getAllDoctor();
			return new ResponseEntity<List<Doctor>>(listofAllDoctor, HttpStatus.OK);
		} catch (BusinessException e) {

			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException(Constant.EXCEPTIONCODE608,
					Constant.EXCEPTION613 + e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}

	}

	// get all doctor end
	// delete by DoctorById
	@DeleteMapping("/deletedoctorById/{doctorId}")
	public ResponseEntity<?> deletedoctorById(@PathVariable("doctorId") Long doctorId) {
		try {
			this.helloDoctorServices.deletedoctorById(doctorId);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (BusinessException e) {
			// TODO: handle exception
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			// TODO: handle exception
			ControllerException ce = new ControllerException(Constant.EXCEPTIONCODE608,
					Constant.EXCEPTION613 + e.getMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}

	}
	// delete by DoctorById End

}
