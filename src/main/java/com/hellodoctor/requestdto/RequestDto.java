package com.hellodoctor.requestdto;

import java.util.Date;

import com.hellodoctor.entities.HospitalAddress;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
	private Long doctorId;
	private String doctorName;
	private String doctorEmail;
	private Long doctorMobileNumber;
	private String doctorPassword;
	private String doctorGender;
	private String doctorSpecilzation;
	private Date registerDate;
	private Date updateDate;
	private String Role;
	private String hospitalName;
	
	private HospitalAddress hospitalAddress;
	
	// for hospital details 
	private Long hospitalId;
	//private String hospitalDetailshospitalName;
	private int noOfBeds;
	private int noOfIcu;
	private int noOfOt;
	private String emergency;
	private Long contactnumber;
	
	// for address details of hospitals
	
	private Long addressId;
	private String addressName;
	private String state;
	private String city;
	private String country;
	private int zipCode;
	

}
