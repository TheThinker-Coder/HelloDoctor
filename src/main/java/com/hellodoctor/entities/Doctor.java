package com.hellodoctor.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "doctor")
public class Doctor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long doctorId;
	private String doctorName;
	@Column(unique = true)
	private String doctorEmail;
	@Column(unique = true)
	private Long doctorMobileNumber;
	private String doctorPassword;
	private String doctorGender;
	private String doctorSpecilzation;
	@Temporal(TemporalType.TIMESTAMP)
	private Date registerDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	private String Role;
	private String hospitalName;
	private String resetPasswordToken;
	
	@OneToOne(mappedBy = "doctorId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private Users userId;
	@ManyToOne
	private HospitalsDetails hospitalsDetails;
	
	@OneToMany(mappedBy ="doctor" )
	private List<Appointment> appointment;

}
