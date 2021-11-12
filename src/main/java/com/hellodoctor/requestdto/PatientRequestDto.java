package com.hellodoctor.requestdto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequestDto {
	
	private String patientName;
	private String patientEmail;
	private String patientPassword;
	private Long patientMobileNumber;
	private Date registerDate;
	private Date updateDate;

}
