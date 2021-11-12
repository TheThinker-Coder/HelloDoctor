package com.hellodoctor.servicesimplements;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.hellodoctor.entities.Appointment;
import com.hellodoctor.entities.Doctor;
import com.hellodoctor.entities.Patient;
import com.hellodoctor.exception.RecordNotFoundException;
import com.hellodoctor.repository.AppointmentRepository;
import com.hellodoctor.repository.DoctorRepository;
import com.hellodoctor.repository.PatientRepository;
import com.hellodoctor.requestdto.AppointmentRequestDto;
import com.hellodoctor.responsedto.AppointmentResponceDto;
import com.hellodoctor.services.AppointmentSerevice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentSerevice {

	
	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private DoctorRepository doctorRepository;

	@Override
	public AppointmentResponceDto saveAppointment(AppointmentRequestDto appointmentRequestDto) {
		
		log.info("start saveAppointment serviceImpl method");
		AppointmentResponceDto appointmentResponceDto = new AppointmentResponceDto();
		Appointment appointment = new Appointment();
		
		Patient registerdPatient = patientRepository.findByPatientEmail(appointmentRequestDto.getPatientEmail());
		
		if(ObjectUtils.isEmpty(registerdPatient)) {
			log.info(" registerdPatient serviceImpl method "+appointmentRequestDto.getPatientEmail());
			throw new RecordNotFoundException("paitent not found");
		}
		
		Doctor doctor = doctorRepository.findBydoctorEmail(appointmentRequestDto.getDoctorEmail());
		
		if(ObjectUtils.isEmpty(doctor)) {
			log.info(" doctor not found "+appointmentRequestDto.getDoctorEmail());
			throw new RecordNotFoundException("doctor not found");
		}
		
		appointment.setDoctor(doctor);
		appointment.setPatient(registerdPatient);
		appointment.setPatientEmail(registerdPatient.getPatientEmail());
		appointment.setPatientName(registerdPatient.getPatientName());
		appointment.setPatientMobileNo(registerdPatient.getPatientMobileNumber());
		appointment.setDoctorName(doctor.getDoctorName());
		appointment.setDoctorEmail(doctor.getDoctorEmail());
		appointment.setFile(appointmentRequestDto.getFileAttech());
		appointment.setAppointmentDate(appointmentRequestDto.getAppointmentDate());
		appointment.setTime(appointmentRequestDto.getTime());
		
		log.info(" save appointment "+appointmentRequestDto.getDoctorEmail());
		Appointment saveAppointment = appointmentRepository.save(appointment);
		
		appointmentResponceDto.setAppointmentId(saveAppointment.getAppointmentId());
		appointmentResponceDto.setDoctorId(saveAppointment.getDoctor().getDoctorId());
		appointmentResponceDto.setDoctorEmail(saveAppointment.getDoctorEmail());
		appointmentResponceDto.setDoctorName(saveAppointment.getDoctorName());
		appointmentResponceDto.setPatientId(saveAppointment.getPatient().getPatientId());
		appointmentResponceDto.setPatientName(saveAppointment.getPatientName());
		appointmentResponceDto.setPatientEmail(saveAppointment.getPatientEmail());
		appointmentResponceDto.setPatientMobileNo(saveAppointment.getPatientMobileNo());
		appointmentResponceDto.setAppointmentDate(saveAppointment.getAppointmentDate());;
		appointmentResponceDto.setTime(appointmentRequestDto.getTime());
		UriComponentsBuilder filePath = ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(appointmentRequestDto.getFileAttech());
		appointmentResponceDto.setFile(filePath.toUriString());
		log.info(" return saveAppointment serviceImpl method "+appointmentRequestDto.getDoctorEmail());
		return appointmentResponceDto;
	}

	@Override
	public List<AppointmentResponceDto> getAppointmentByPatientEmail(String byPatientEmail) {
		log.info("start getAppointmentByPatientEmail serviceImpl method");
		List<AppointmentResponceDto> appointmentResponceDto = new ArrayList<>();
		
		log.info(" fetchin appointment ");
		List<Appointment> appointmentDetails = appointmentRepository.findByPatientEmail(byPatientEmail);
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
