package com.hellodoctor.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Hospitaladdress")
public class HospitalAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long addressId;
	private String addressName;
	private String State;
	private String city;
	private String Country;
	private int zipCode;

	

}
