package com.hellodoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hellodoctor.entities.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}
