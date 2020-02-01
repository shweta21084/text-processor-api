package com.optus.apis.textprocessor.security;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.User;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(AuthenticationFilter.class);
	
	private AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		User user = null;
		try {
			user = new ObjectMapper().readValue(request.getInputStream(), User.class);

			return authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

		} catch (BadCredentialsException e ) {
			// Authentication has failed.
			logger.error("Authentication Failed for the username: {} ", user.getUsername(), e.getMessage());
			throw e;
		} catch (JsonMappingException e) {
			logger.error("Error while json mapping for authentication ", e);
		} catch (JsonParseException e) {
			logger.error("Error while json parsing for authenication ", e);
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("I/O Error while authenicating ", e);
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		User user = (User) authResult.getPrincipal();
		
		String base64Credentials = user.getUsername() + ":" + user.getPassword();
		
		byte[] token = Base64.getEncoder().encode(base64Credentials.getBytes());
		
		response.addHeader(SecurityContants.AUTHORIZATION_HEADER, SecurityContants.BASIC_TOKEN_PREFIX + token);
		
	}
}
