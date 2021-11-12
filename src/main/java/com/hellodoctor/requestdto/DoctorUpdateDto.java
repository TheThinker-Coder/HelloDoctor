package com.hellodoctor.requestdto;

import java.util.Date;

import com.hellodoctor.entities.Users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorUpdateDto {
	
	private String doctorName;
	private String doctorEmail;
	private Long doctorMobileNumber;
	private String doctorPassword;
	private Date updateDate;
	private String hospitalName;
	private Users users;

}
