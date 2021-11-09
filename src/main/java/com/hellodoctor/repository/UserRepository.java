package com.hellodoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hellodoctor.entities.Users;
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
	
	
	public Users findByEmail(String email);
	
	

}
