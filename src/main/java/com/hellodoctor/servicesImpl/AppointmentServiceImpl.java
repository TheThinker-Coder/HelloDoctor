package com.hellodoctor.servicesImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.hellodoctor.constant.Constant;
import com.hellodoctor.entities.Appointment;
import com.hellodoctor.entities.Doctor;
import com.hellodoctor.entities.Patient;
import com.hellodoctor.exception.RecordNotFoundException;
import com.hellodoctor.repository.AppointmentRepository;
import com.hellodoctor.repository.DoctorRepository;
import com.hellodoctor.repository.PatientRepository;
import com.hellodoctor.requestdto.AppointmentRequestDto;
import com.hellodoctor.responsedto.AppointmentResponceDto;
import com.hellodoctor.services.AppointmentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Override
	public AppointmentResponceDto saveAppointment(AppointmentRequestDto appointmentRequestDto) {

		log.info("start saveAppointment serviceImpl");
		AppointmentResponceDto appointmentResponceDto = new AppointmentResponceDto();
		Appointment appointment = new Appointment();

		Patient registerdPatient = patientRepository.findByPatientEmail(appointmentRequestDto.getPatientEmail());

		if (ObjectUtils.isEmpty(registerdPatient)) {
			log.info(Constant.PATIENT_NOT_FOUND + appointmentRequestDto.getPatientEmail());
			throw new RecordNotFoundException(Constant.PATIENT_NOT_FOUND);
		}

		Doctor doctor = doctorRepository.findBydoctorEmail(appointmentRequestDto.getDoctorEmail());

		if (ObjectUtils.isEmpty(doctor)) {
			log.info(Constant.DOCTOR_NOT_FOUND + appointmentRequestDto.getDoctorEmail());
			throw new RecordNotFoundException(Constant.DOCTOR_NOT_FOUND);
		}

		appointment.setDoctor(doctor);
		appointment.setPatient(registerdPatient);
		appointment.setPatientEmail(registerdPatient.getPatientEmail());
		appointment.setPatientName(registerdPatient.getPatientName());
		appointment.setPatientMobileNo(registerdPatient.getPatientMobileNumber());
		appointment.setDoctorName(doctor.getDoctorName());
		appointment.setDoctorEmail(doctor.getDoctorEmail());
		String filePath = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
				.path(appointmentRequestDto.getFileAttech()).toUriString();
		appointment.setFile(filePath);
		appointment.setAppointmentDate(appointmentRequestDto.getAppointmentDate());
		appointment.setTime(appointmentRequestDto.getTime());

		log.info(" save appointment " + appointmentRequestDto.getDoctorEmail());
		Appointment saveAppointment = appointmentRepository.save(appointment);
		BeanUtils.copyProperties(saveAppointment, appointmentResponceDto);

		log.info(" change object entity to response ");

		appointmentResponceDto.setDoctorId(saveAppointment.getDoctor().getDoctorId());
		appointmentResponceDto.setTime(appointmentRequestDto.getTime());
		appointmentResponceDto.setPatientId(saveAppointment.getPatient().getPatientId());
		appointmentResponceDto.setFile(filePath);
		log.info(" return saveAppointment serviceImpl method " + appointmentRequestDto.getDoctorEmail());
		return appointmentResponceDto;
	}

	@Override
	public List<AppointmentResponceDto> getAppointmentByPatientEmail(String byPatientEmail) {
		log.info("start getAppointmentByPatientEmail serviceImpl method");
		List<AppointmentResponceDto> appointmentResponceDto = new ArrayList<>();

		log.info(" fetchin appointment ");
		List<Appointment> appointmentDetails = appointmentRepository.findByPatientEmail(byPatientEmail);
		if (ObjectUtils.isEmpty(appointmentDetails)) {
			throw new RecordNotFoundException(Constant.APPOINTMENT_NOT_FOUND);
		}
		for (Appointment appDeatiels : appointmentDetails) {
			AppointmentResponceDto dto = new AppointmentResponceDto();
			BeanUtils.copyProperties(appDeatiels, dto);

			dto.setDoctorId(appDeatiels.getDoctor().getDoctorId());
			dto.setPatientId(appDeatiels.getPatient().getPatientId());
			appointmentResponceDto.add(dto);
		}

		log.info(" return getAppointmentByPatientEmail serviceImpl method ");
		return appointmentResponceDto;
	}

}
