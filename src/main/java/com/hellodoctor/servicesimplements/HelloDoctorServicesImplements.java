package com.hellodoctor.servicesimplements;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.hellodoctor.constant.Constant;
import com.hellodoctor.entities.Appointment;
import com.hellodoctor.entities.Doctor;
import com.hellodoctor.entities.HospitalAddress;
import com.hellodoctor.entities.HospitalsDetails;
import com.hellodoctor.entities.Users;
import com.hellodoctor.exception.BusinessException;
import com.hellodoctor.exception.EmptyInputException;
import com.hellodoctor.exception.RecordNotFoundException;
import com.hellodoctor.repository.AppointmentRepository;
import com.hellodoctor.repository.DoctorRepository;
import com.hellodoctor.repository.HospitalAddressRepository;
import com.hellodoctor.repository.HospitalsDetailsRepository;
import com.hellodoctor.repository.UsersRepository;
import com.hellodoctor.requestdto.DoctorUpdateDto;
import com.hellodoctor.requestdto.RequestDto;
import com.hellodoctor.responsedto.AppointmentResponceDto;
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
	
	@Autowired
	private AppointmentRepository appointmentRepository;

	// doctorUpdateService
	@Override
	public Doctor updateDoctor(DoctorUpdateDto doctorUpdateDto, String doctorEmail) {
		log.info("inside Doctor Update Service");
		Doctor findBydoctorEmail = doctorRepository.findBydoctorEmail(doctorEmail);
		Users findById = usersRepository.findById(findBydoctorEmail.getUserId().getUserId()).orElse(null);
		// Doctor doctor = new Doctor();
		if (findBydoctorEmail != null) {
			findBydoctorEmail.setDoctorName(doctorUpdateDto.getDoctorName());
			findBydoctorEmail.setDoctorMobileNumber(doctorUpdateDto.getDoctorMobileNumber());
			findBydoctorEmail.setDoctorPassword(doctorUpdateDto.getDoctorPassword());
			findBydoctorEmail.setHospitalName(doctorUpdateDto.getHospitalName());
			findBydoctorEmail.setUpdateDate(new Date());
			log.info("saving Doctor Info In Doctor Entity");
			doctorRepository.save(findBydoctorEmail);
		}
		if (findById != null) {
			findById.setEmail(findBydoctorEmail.getDoctorEmail());
			findById.setMobile(doctorUpdateDto.getDoctorMobileNumber());
			findById.setPassword(doctorUpdateDto.getDoctorPassword());
			usersRepository.save(findById);
		} else {
			throw new EmptyInputException(Constant.EXCEPTIONEMAILVALIDATION);
		}
		return findBydoctorEmail;

	}
	// doctorUpdateServiceEnd

	@Override
	public Doctor addDoctor(RequestDto requestDto) {
		log.info("inside add doctor service");
		if (requestDto.getDoctorName().isEmpty() || requestDto.getDoctorEmail().isEmpty()
				|| requestDto.getDoctorName().length() == 0) {
			log.error("error Empty Input");
			throw new EmptyInputException(Constant.EXCEPTION601);
		}
		try {
			log.info("inside try block of add Doctor in serviceImpl");
			Doctor doctor = new Doctor(); // for setting feilds in doctor entity
			doctor.setDoctorName(requestDto.getDoctorName());
			doctor.setDoctorMobileNumber(requestDto.getDoctorMobileNumber());
			doctor.setDoctorEmail(requestDto.getDoctorEmail());
			doctor.setDoctorPassword(
					(Base64.getEncoder().encodeToString(requestDto.getDoctorPassword().toString().getBytes()))); // used
			// password
			doctor.setDoctorGender(requestDto.getDoctorGender());
			doctor.setDoctorSpecilzation(requestDto.getDoctorSpecilzation());
			doctor.setHospitalName(requestDto.getHospitalName());
			doctor.setRegisterDate(new Date());
			doctor.setUpdateDate(new Date());
			doctor.setRole(Constant.DOCTORROLE);
			Doctor savedoctor = doctorRepository.save(doctor); // for saving doctor and calling doctor id
			log.info("checking or matching hospital address exits otr not for saving it into doctor");
			String hospitalAddress = requestDto.getHospitalName();
			HospitalsDetails address1 = new HospitalsDetails();
			address1 = hospitalsDetailsRepository.findByhospitalName(hospitalAddress);
			log.info("hospital address is = " + hospitalAddress);
			log.info("database hospital address is " + address1);
			if (ObjectUtils.isEmpty(address1)) {
				log.error("current address not found  " + address1);
				throw new RecordNotFoundException("current address is  not registerd");
			}
			// Long address1Id = address1.getHospitalId();
			doctor.setHospitalsDetails(address1);
			log.info("calling user object");
			Users users = new Users(); // for setting feilds in user table
			users.setDoctorId(savedoctor);
			users.setEmail(requestDto.getDoctorEmail());
			users.setMobile(requestDto.getDoctorMobileNumber());
			users.setPassword(
					(Base64.getEncoder().encodeToString(requestDto.getDoctorPassword().toString().getBytes())));// used
																												// //
																												// password
			users.setRole(Constant.DOCTORROLE);
			log.info("saving doctor");
			log.info("saving user");
			usersRepository.save(users);// for saving user
			return doctor;
		} catch (IllegalArgumentException e) {
			log.info("inside adddoctor hospitlalservice IllegalArgumentExceptionCatchBlock");
			throw new BusinessException(e.getMessage());
		} catch (Exception ex) {
			log.info("inside adddoctor hosptialServicesImplements ExceptionBlock");
			throw new BusinessException(ex.getMessage());
		}
	}

	// for saving hospital info
	@Override
	public HospitalsDetails addHospital(RequestDto requestDto) {
		log.info("inside addHospital service ");
		if (requestDto.getHospitalName().isEmpty() || requestDto.getHospitalName().length() == 0) {
			log.error("addhopsital Error called");
			throw new EmptyInputException(Constant.EXCEPTION601);
		}

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

	}
	// hospital info end

	// for getting hospital details via address mapping

	@Override
	public HospitalsDetails getHospitalById(Long hospitalId) {
		try {
			return hospitalsDetailsRepository.findById(hospitalId).get();
		} catch (IllegalArgumentException e) {
			log.info("insideIllegalArgumentExceptionCatchBlock");
			throw new BusinessException(Constant.EXCEPTION606 + e.getMessage());
		} catch (NoSuchElementException e) {
			throw new BusinessException(Constant.EXCEPTION607 + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException(Constant.EXCEPTION608 + e.getMessage());
		}
	}

	// get doctor by email
	@Override
	public Doctor findBydoctorEmail(String doctorEmail) {
		try {
			return doctorRepository.findBydoctorEmail(doctorEmail);
		} catch (IllegalArgumentException e) {
			log.info("insideIllegalArgumentExceptionCatchBlock");
			throw new BusinessException(Constant.EXCEPTION621 + e.getMessage());
		} catch (NoSuchElementException e) {
			throw new BusinessException(Constant.EXCEPTION620 + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException(Constant.EXCEPTION608 + e.getMessage());
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
			throw new BusinessException(Constant.EXCEPTION606 + e.getMessage());
		} catch (NoSuchElementException e) {
			throw new BusinessException(Constant.EXCEPTION607 + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException(Constant.EXCEPTION608 + e.getMessage());
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
			throw new BusinessException(Constant.EXCEPTION606 + e.getMessage());
		} catch (NoSuchElementException e) {
			throw new BusinessException(Constant.EXCEPTION607 + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException(Constant.EXCEPTION608 + e.getMessage());
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
			throw new BusinessException(Constant.EXCEPTION618);
		}
		if (hList.isEmpty())
			throw new BusinessException(Constant.EXCEPTION604);
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
			throw new BusinessException(Constant.EXCEPTION618);
		}
		if (doctorList.isEmpty())
			throw new BusinessException(Constant.EXCEPTION604);
		log.info("doctor list object");
		return doctorList;
	}
	// get all doctor end

	// delete by DoctorById
	@Override
	public void deletedoctorById(Long doctorId) {
		try {
			doctorRepository.deleteById(doctorId);

		} catch (IllegalArgumentException e) {
			log.info("inside addEmployee AttendenceServicesImplements IllegalArgumentExceptionCatchBlock");
			throw new BusinessException(Constant.EXCEPTION609 + e.getMessage());
		} catch (Exception e) {
			throw new BusinessException(Constant.EXCEPTION613 + e.getMessage());
		}

	}
	// delete by DoctorById End

	// get hospital by name
	@Override
	public List<HospitalsDetails> findByhospitalName(String hospitalName) {
//		List<HospitalsDetails> hospitallist = null;
//		hospitallist = hospitalsDetailsRepository.findByhospitalName(hospitalName);
//		try {
//			hospitallist = hospitalsDetailsRepository.findByhospitalName(hospitalName);
//		} catch (Exception e) {
//			throw new BusinessException(Constant.EXCEPTION618);
//		}
//		if (hospitallist.isEmpty())
//			throw new BusinessException(Constant.EXCEPTION604);
//		log.info("doctor list object");
//		return hospitallist;
		return null;
	}

	@Override
	public void updateResetPasswordToken(String token, String email) throws RecordNotFoundException {
		System.out.println(email);
		Doctor user = doctorRepository.findBydoctorEmail(email);
		if (user != null) {
			user.setResetPasswordToken(token);
			doctorRepository.save(user);
		} else {
			throw new RecordNotFoundException("Cant find the user with this email" + email);
		}
	}

	@Override
	public Doctor getByResetPasswordToken(String token) {
		return doctorRepository.findByResetPasswordToken(token);
	}

	@Override
	public void updatePassword(Doctor user, String newPassword) {
		user.setDoctorPassword(newPassword);
		Users findById = usersRepository.findById(user.getUserId().getUserId()).orElse(null);
		user.setResetPasswordToken(null);
		doctorRepository.save(user);
		if (findById != null) {
			// Users users = new Users();
			findById.setPassword(user.getDoctorPassword());
			usersRepository.save(findById);

	}

	// get hospital by name end

	// for getting hospital details via address mapping end

	}

	@Override
	public List<AppointmentResponceDto> getAppointment(String email) {
		Doctor doctor = doctorRepository.findBydoctorEmail(email);
List<AppointmentResponceDto> appointmentResponceDto = new ArrayList<>();
		
		log.info(" fetching appointment ");
		List<Appointment> appointmentDetails = appointmentRepository.findByDoctorEmail(doctor.getDoctorEmail());
		if(ObjectUtils.isEmpty(appointmentDetails)) {
			throw new RecordNotFoundException("appointment is empty");
		}
		for(Appointment appDeatiels :appointmentDetails) {
			AppointmentResponceDto dto = new AppointmentResponceDto();
			dto.setDoctorId(appDeatiels.getDoctor().getDoctorId());
			dto.setPatientId(appDeatiels.getPatient().getPatientId());
			dto.setAppointmentId(appDeatiels.getAppointmentId());
			dto.setPatientEmail(appDeatiels.getPatientEmail());
			dto.setPatientName(appDeatiels.getPatientName());
			dto.setPatientMobileNo(appDeatiels.getPatientMobileNo());
			dto.setDoctorName(appDeatiels.getDoctorName());
			dto.setDoctorEmail(appDeatiels.getDoctorEmail());
			dto.setAppointmentDate(appDeatiels.getAppointmentDate());
			dto.setTime(appDeatiels.getTime());
			dto.setFile(appDeatiels.getFile());
			appointmentResponceDto.add(dto);
		}
		
		log.info(" return getAppointmentByPatientEmail serviceImpl method ");
		return appointmentResponceDto;
	}
}
