package com.hellodoctor.servicesimplements;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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

		log.info("Start UserDetailsServiceImpl in loadUserByUsername");
		
		Users user = userRepository.findByEmail(email);
		
		log.info("Fetching user login data using database findByEmail");
		
		if (ObjectUtils.isEmpty(user)) {
			log.error("User not found with email: {}", email);
			throw new UsernameNotFoundException("User not found with userName: " + email);
		}
		String password = Base64.getEncoder().encodeToString(user.getPassword().toString().getBytes());
		List<SimpleGrantedAuthority> list = new ArrayList<>();
		SimpleGrantedAuthority role = new SimpleGrantedAuthority(user.getRole());
		list.add(role);
		
		log.info("userDatails return for loadUserByUsername");
		return new User(user.getEmail(), password, list);
	}

}
