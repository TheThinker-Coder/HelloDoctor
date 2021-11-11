package com.hellodoctor.entities;

import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "hospitalsdetails")
public class HospitalsDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long hospitalId;
	private String hospitalName;
	private int noOfBeds;
	private int noOfIcu;
	private int noOfOt;
	private String Emergency;
	private Long contactNumber;
	
	@OneToMany(mappedBy = "hospitalsDetails")
	private List<Doctor> doctor;
	
	@OneToOne(cascade = CascadeType.ALL)
	private HospitalAddress hospitalAddress;

}
