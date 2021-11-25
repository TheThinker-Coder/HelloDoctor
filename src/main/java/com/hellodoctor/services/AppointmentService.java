package com.hellodoctor.services;

import java.util.List;

import com.hellodoctor.requestdto.AppointmentRequestDto;
import com.hellodoctor.responsedto.AppointmentResponceDto;

public interface AppointmentService {

	AppointmentResponceDto saveAppointment(AppointmentRequestDto appointmentRequestDto);

	List<AppointmentResponceDto> getAppointmentByPatientEmail(String byPatientEmail);

}
	