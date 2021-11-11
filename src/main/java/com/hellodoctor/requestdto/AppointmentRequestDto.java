package com.hellodoctor.requestdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDto {

	private String patientEmail;
	private Long patientMobileNo;
	private String time;
	private String fileAttech;
	private String doctorEmail;
	
	
}
