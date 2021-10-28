package com.hellodoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hellodoctor.entities.ContactUs;

public interface ContactUsRepository extends JpaRepository<ContactUs, Long> {

}
