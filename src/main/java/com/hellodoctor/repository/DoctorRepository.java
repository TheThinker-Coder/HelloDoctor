package com.hellodoctor.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.hellodoctor.entities.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
	 
	
	public Doctor findBydoctorEmail(String doctorEmail);
	 public Doctor findByResetPasswordToken(String token);
	public Doctor findByDoctorMobileNumber(Long doctorMobileNumber);
}
