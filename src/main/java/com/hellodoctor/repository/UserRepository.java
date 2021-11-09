package com.hellodoctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hellodoctor.entities.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	//public User findByEmailAndPassword(String email,String password);
	public User findByEmail(String email);
	
	@Query("select u from User u where u.email = :email")
	public User getUserByuUserName(@Param("email") String email);

}
