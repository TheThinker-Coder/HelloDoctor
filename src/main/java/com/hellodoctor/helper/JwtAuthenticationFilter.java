package com.hellodoctor.helper;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hellodoctor.servicesimplements.UserDetailsServiceImpl;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 String requestTokenHeader = request.getHeader("Authorization");
			final String refreshTokenHeader = request.getHeader("AuthorizationRefresh");

			String username = null;
			String jwtToken = null;
			String jwtRefreshToken = null;
			// JWT Token is in the form "Bearer token". Remove Bearer word and get
			// only the Token
			if ((requestTokenHeader != null && requestTokenHeader.startsWith("Bearer"))
					|| (refreshTokenHeader != null && refreshTokenHeader.startsWith("Bearer"))) {
				jwtToken = requestTokenHeader.substring(7);
				jwtRefreshToken = requestTokenHeader.substring(7);
				username = jwtUtil.getUsernameFromToken(jwtRefreshToken);
			} else {
				log.warn("JWT Token does not begin with Bearer String");
			}

			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

				// add && userDetails.isCredentialsNonExpired() in below if condition to apply
				// password expired filter in all requests

				if (jwtUtil.validateToken(jwtToken, userDetails)
						&& jwtUtil.validateToken(jwtRefreshToken, userDetails)) {
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());

					usernamePasswordAuthenticationToken
							.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
			filterChain.doFilter(request, response);

	}

}
