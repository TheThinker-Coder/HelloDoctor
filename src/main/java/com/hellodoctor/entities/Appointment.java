package com.hellodoctor.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "appointment")
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long appointmentId;
	private String patientName;
	private String patientEmail;
	private String	doctorName;
	private Long patientMobileNo;
	private Date appointmentDate;
	private String time;
	private String File;
	@ManyToOne
	private Doctor doctor;
	@ManyToOne
	private Patient patient;

}
