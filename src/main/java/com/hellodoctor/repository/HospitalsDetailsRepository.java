package com.hellodoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hellodoctor.entities.HospitalsDetails;

public interface HospitalsDetailsRepository extends JpaRepository<HospitalsDetails, Long> {
	
	//public List<HospitalsDetails>findByhospitalName(String hospitalName);
	public HospitalsDetails findByhospitalName(String hospitalName);

}
