package com.hellodoctor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hellodoctor.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	List<Appointment> findByPatientEmail(String patientEmail);

	List<Appointment> findByDoctorEmail(String doctorEmail);

}
