package com.hellodoctor.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hellodoctor.configuration.CustomerDeatils;
import com.hellodoctor.entities.Users;
import com.hellodoctor.repository.UsersRepository;

@Service
public class UserDetailsServiceImplements implements UserDetailsService {

	@Autowired
	private UsersRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("could not found user");
		}
		CustomerDeatils customerDeatils = new CustomerDeatils(user);
		return customerDeatils;
	}

}
