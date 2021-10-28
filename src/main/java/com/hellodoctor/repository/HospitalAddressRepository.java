package com.hellodoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hellodoctor.entities.HospitalAddress;

public interface HospitalAddressRepository extends JpaRepository<HospitalAddress, Long> {

}
