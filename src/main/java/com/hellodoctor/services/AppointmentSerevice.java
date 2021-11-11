package com.hellodoctor.services;

import com.hellodoctor.requestdto.AppointmentRequestDto;
import com.hellodoctor.responsedto.AppointmentResponceDto;

public interface AppointmentSerevice {

	AppointmentResponceDto saveAppointment(AppointmentRequestDto appointmentRequestDto);

}
	