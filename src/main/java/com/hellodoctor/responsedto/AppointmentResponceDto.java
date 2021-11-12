package com.hellodoctor.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponceDto {
	
	private Long appointmentId;
	private String patientName;
	private String patientEmail;
	private String	doctorName;
	private String	doctorEmail;
	private Long patientMobileNo;
	private String appointmentDate;
	private String time;
	private String File;
	private Long doctorId;
	private Long patientId;
}
