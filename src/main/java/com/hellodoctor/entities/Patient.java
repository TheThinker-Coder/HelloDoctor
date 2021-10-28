package com.hellodoctor.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patient")
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long patientId;
	private String patientName;
	@Column(unique = true)
	private String patientEmail;
	private String patientPassword;
	@Column(unique = true)
	private Long patientMobileNumber;
	private Date registerDate;
	private Date updatedDate;
	private String role;
	@OneToOne(mappedBy = "patientId", cascade = CascadeType.ALL)
	private Users userId;

	@OneToMany(mappedBy = "patient")
	private List<Appointment> appointment;

	@OneToMany(mappedBy = "patience")
	private List<ContactUs> contactUs;

}
