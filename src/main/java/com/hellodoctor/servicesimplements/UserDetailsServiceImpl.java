package com.hellodoctor.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.hellodoctor.entities.Users;
import com.hellodoctor.repository.UsersRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsersRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Users user = userRepository.findByEmail(email);
		if (ObjectUtils.isEmpty(user)) {
			log.error("User not found with email: {}", email);
			throw new UsernameNotFoundException("User not found with userName: " + email);
		}
		
		CustomeUserDetails userDetails = new CustomeUserDetails(user);
		return userDetails;
	}

}
