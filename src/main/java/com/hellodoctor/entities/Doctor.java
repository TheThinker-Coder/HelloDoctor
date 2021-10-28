package com.hellodoctor.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long doctorId;
	private String doctorName;
	private String doctorEmail;
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

	@ManyToOne
	private HospitalsDetails hospitalsDetails;
	
	@OneToMany(mappedBy ="doctor" )
	private List<Appointment> appointment;

}
