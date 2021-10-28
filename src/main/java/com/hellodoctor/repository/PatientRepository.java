package com.hellodoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hellodoctor.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long>{

	public Patient findByPatientEmail(String patientEmail);

}
