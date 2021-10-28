package com.hellodoctor.responsedto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponseDto {
	private long patientId;
	private String patientName;
	private String patientEmail;
	private Long patientMobileNumber;
	private Date registerDate;
	private Date updatedDate;
	private String PatientPassword;
	private String role;

}
