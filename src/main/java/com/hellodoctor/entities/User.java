package com.hellodoctor.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	@Column(unique = true)
	private String email;
	private String password;
	@Column(unique = true)
	private Long mobile;
	private String role;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "patientId")
	private Patient patientId; 
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "doctorId")
	private Doctor doctorId;

}
