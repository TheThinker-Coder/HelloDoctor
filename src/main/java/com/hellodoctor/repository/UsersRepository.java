package com.hellodoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hellodoctor.entities.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

	Users findByEmail(String username);

}
