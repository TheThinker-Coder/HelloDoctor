package com.hellodoctor.servicesimplements;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hellodoctor.constant.Constant;
import com.hellodoctor.entities.Doctor;
import com.hellodoctor.entities.HospitalAddress;
import com.hellodoctor.entities.HospitalsDetails;
import com.hellodoctor.entities.Users;
import com.hellodoctor.exception.BusinessException;
import com.hellodoctor.exception.EmptyInputException;
import com.hellodoctor.repository.DoctorRepository;
import com.hellodoctor.repository.HospitalAddressRepository;
import com.hellodoctor.repository.HospitalsDetailsRepository;
import com.hellodoctor.repository.UsersRepository;
import com.hellodoctor.requestdto.RequestDto;
import com.hellodoctor.services.HelloDoctorServices;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HelloDoctorServicesImplements implements HelloDoctorServices {
	@Autowired
	private DoctorRepository doctorRepository;
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private HospitalsDetailsRepository hospitalsDetailsRepository;
	@Autowired
	private HospitalAddressRepository hospitalAddressRepository;

	@Override
	public Doctor addDoctor(RequestDto requestDto) {
		log.info("inside add doctor service");
		if (requestDto.getDoctorName().isEmpty() || requestDto.getDoctorEmail().isEmpty()
				|| requestDto.getDoctorName().length() == 0) {
			log.error("error Empty Input");
			throw new EmptyInputException(Constant.EXCEPTIONCODE601, Constant.EXCEPTION601);
		}
		try {
			log.info("inside try block of add Doctor in serviceImpl");
			Doctor doctor = new Doctor(); // for setting feilds in doctor entity
			doctor.setDoctorName(requestDto.getDoctorName());
			doctor.setDoctorMobileNumber(requestDto.getDoctorMobileNumber());
			doctor.setDoctorEmail(requestDto.getDoctorEmail());
			doctor.setDoctorPassword(requestDto.getDoctorPassword());
			doctor.setDoctorGender(requestDto.getDoctorGender());
			doctor.setDoctorSpecilzation(requestDto.getDoctorSpecilzation());
			doctor.setHospitalName(requestDto.getHospitalName());
			doctor.setRegisterDate(new Date());
			doctor.setUpdateDate(new Date());
			doctor.setRole(Constant.DOCTORROLE);
			log.info("calling user object");
			Users users = new Users(); // for setting feilds in user table
			users.setEmail(requestDto.getDoctorEmail());
			users.setMobile(requestDto.getDoctorMobileNumber());
			users.setPassword(requestDto.getDoctorPassword());
			users.setRole(Constant.DOCTORROLE);
			log.info("saving doctro");
			doctorRepository.save(doctor); // for saving doctor
			log.info("saving user");
			usersRepository.save(users);// for saving user
			return doctor;
		} catch (IllegalArgumentException e) {
			log.info("inside addEmployee AttendenceServicesImplements IllegalArgumentExceptionCatchBlock");
			throw new BusinessException(Constant.EXCEPTIONCODE602, Constant.EXCEPTION602 + e.getMessage());
		} catch (Exception ex) {
			log.info("inside addEmployee AttendenceServicesImplements ExceptionBlock");
			throw new BusinessException(Constant.EXCEPTIONCODE603, Constant.EXCEPTION603 + ex.getMessage());
		}

	}

	// for saving hospital info
	@Override
	public HospitalsDetails addHospital(RequestDto requestDto) {
		log.info("inside addHospital service ");
		if (requestDto.getHospitalName().isEmpty() || requestDto.getHospitalName().length() == 0) {
			log.error("addhopsital Error called");
			throw new EmptyInputException(Constant.EXCEPTIONCODE601, Constant.EXCEPTION601);
		}
		try {
			log.info("inside try method of addhospital service");
			HospitalsDetails hospitalsDetails = new HospitalsDetails();
			hospitalsDetails.setHospitalName(requestDto.getHospitalName());
			hospitalsDetails.setNoOfBeds(requestDto.getNoOfBeds());
			hospitalsDetails.setNoOfIcu(requestDto.getNoOfIcu());
			hospitalsDetails.setNoOfOt(requestDto.getNoOfOt());
			hospitalsDetails.setEmergency(requestDto.getEmergency());
			hospitalsDetails.setContactNumber(requestDto.getContactnumber());
			HospitalAddress hospitalAddress = new HospitalAddress(); // for saving hospital address using mapping
			hospitalAddress.setAddressName(requestDto.getAddressName());
			hospitalAddress.setCity(requestDto.getCity());
			hospitalAddress.setState(requestDto.getState());
			hospitalAddress.setZipCode(requestDto.getZipCode());
			hospitalAddress.setCountry(requestDto.getCountry());
			log.info("object of address set in hospital");
			hospitalsDetails.setHospitalAddress(hospitalAddress);
			log.info("object of hospitalsDetails save in hospitalsDetails");
			hospitalsDetailsRepository.save(hospitalsDetails);
			log.info("object of hospitalAddress save in hospitalAddress");
			// hospitalAddressRepository.save(hospitalAddress);
			return hospitalsDetails;
		} catch (IllegalArgumentException e) {
			log.info("inside addEmployee AttendenceServicesImplements IllegalArgumentExceptionCatchBlock");
			throw new BusinessException(Constant.EXCEPTIONCODE602, Constant.EXCEPTION602 + e.getMessage());
		} catch (Exception ex) {
			log.info("inside addEmployee AttendenceServicesImplements ExceptionBlock");
			throw new BusinessException(Constant.EXCEPTIONCODE603, Constant.EXCEPTION603 + ex.getMessage());
		}

	}
	// hospital info end

	// for getting hospital details via address mapping

	@Override
	public HospitalsDetails getHospitalById(Long hospitalId) {
		try {
			return hospitalsDetailsRepository.findById(hospitalId).get();
		} catch (IllegalArgumentException e) {
			log.info("insideIllegalArgumentExceptionCatchBlock");
			throw new BusinessException(Constant.EXCEPTIONCODE606, Constant.EXCEPTION606 + e.getMessage());
		} catch (NoSuchElementException e) {
			throw new BusinessException(Constant.EXCEPTIONCODE607, Constant.EXCEPTION607 + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException(Constant.EXCEPTIONCODE608, Constant.EXCEPTION608 + e.getMessage());
		}
	}
	
	// get doctor by email
	@Override
	public Doctor findBydoctorEmail(String doctorEmail) {
		try {
			return doctorRepository.findBydoctorEmail(doctorEmail);
		} catch (IllegalArgumentException e) {
			log.info("insideIllegalArgumentExceptionCatchBlock");
			throw new BusinessException(Constant.EXCEPTIONCODE621, Constant.EXCEPTION621 + e.getMessage());
		} catch (NoSuchElementException e) {
			throw new BusinessException(Constant.EXCEPTIONCODE620, Constant.EXCEPTION620 + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException(Constant.EXCEPTIONCODE608, Constant.EXCEPTION608 + e.getMessage());
		}
	}
	// get doctor by email end
	// get doctor by id
	@Override
	public Doctor getDoctorById(Long doctorId) {
		try {
			return doctorRepository.findById(doctorId).get();
		} catch (IllegalArgumentException e) {
			log.info("insideIllegalArgumentExceptionCatchBlock");
			throw new BusinessException(Constant.EXCEPTIONCODE606, Constant.EXCEPTION606 + e.getMessage());
		} catch (NoSuchElementException e) {
			throw new BusinessException(Constant.EXCEPTIONCODE607, Constant.EXCEPTION607 + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException(Constant.EXCEPTIONCODE608, Constant.EXCEPTION608 + e.getMessage());
		}
	}

	// get doctor by id end
	
	// get by addressId
	@Override
	public HospitalAddress getHospitalAddressById(Long addressId) {
		try {
			return hospitalAddressRepository.findById(addressId).get();
		} catch (IllegalArgumentException e) {
			log.info("insideIllegalArgumentExceptionCatchBlock");
			throw new BusinessException(Constant.EXCEPTIONCODE606, Constant.EXCEPTION606 + e.getMessage());
		} catch (NoSuchElementException e) {
			throw new BusinessException(Constant.EXCEPTIONCODE607, Constant.EXCEPTION607 + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException(Constant.EXCEPTIONCODE608, Constant.EXCEPTION608 + e.getMessage());
		}
	}
	
	// get by addressId end 

	// get all hospital start
	@Override
	public List<HospitalsDetails> getAllHospital() {
		List<HospitalsDetails> hList = null;
		try {
			hList = hospitalsDetailsRepository.findAll();
		} catch (Exception e) {
			throw new BusinessException(Constant.EXCEPTIONCODE618, Constant.EXCEPTION618);
		}
		if (hList.isEmpty())
			throw new BusinessException(Constant.EXCEPTIONCODE604, Constant.EXCEPTION604);
		log.info("hsplist object get");
		return hList;
	}

	// get all hospital end
	// get all doctor 
	@Override
	public List<Doctor> getAllDoctor() {
		List<Doctor> doctorList = null;
		try {
			doctorList = doctorRepository.findAll();
		} catch (Exception e) {
			throw new BusinessException(Constant.EXCEPTIONCODE618, Constant.EXCEPTION618);
		}
		if (doctorList.isEmpty())
			throw new BusinessException(Constant.EXCEPTIONCODE604, Constant.EXCEPTION604);
		log.info("doctor list object");
		return doctorList;
	}
	//get all doctor end 
	
	// delete by DoctorById
	@Override
	public void deletedoctorById(Long doctorId) {
		try {
			doctorRepository.deleteById(doctorId);
			
		} catch (IllegalArgumentException e) {
			log.info("inside addEmployee AttendenceServicesImplements IllegalArgumentExceptionCatchBlock");
			throw new BusinessException(Constant.EXCEPTIONCODE609, Constant.EXCEPTION609 + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException(Constant.EXCEPTION613, Constant.EXCEPTION613 + e.getMessage());
		}
		
	}
	// delete by DoctorById End
	
	// get hospital by name
	@Override
	public List<HospitalsDetails> findByhospitalName(String hospitalName) {
		List<HospitalsDetails> hospitallist = null;
		hospitallist= hospitalsDetailsRepository.findByhospitalName(hospitalName);
		try{
			hospitallist = hospitalsDetailsRepository.findByhospitalName(hospitalName);
		} catch (Exception e) {
			throw new BusinessException(Constant.EXCEPTIONCODE618, Constant.EXCEPTION618);
		}
		if (hospitallist.isEmpty())
			throw new BusinessException(Constant.EXCEPTIONCODE604, Constant.EXCEPTION604);
		log.info("doctor list object");
		return hospitallist;
	}

	// get hospital by name end 
	
	
		
	
	// for getting hospital details via address mapping end

}
