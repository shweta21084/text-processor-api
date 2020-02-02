package com.optus.apis.textprocessor.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class AuthorizationFilter extends BasicAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		this.authenticationManager = authenticationManager;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String authHeader = request.getHeader(SecurityContants.AUTHORIZATION_HEADER);

		if (authHeader == null || !authHeader.startsWith(SecurityContants.BASIC_TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(authHeader);
		
		authenticationManager.authenticate(authenticationToken);
		
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		chain.doFilter(request, response);

	}

	private UsernamePasswordAuthenticationToken getAuthentication(String authHeader) {

		if (authHeader != null) {
			// Authorization: Basic base64credentials
			String base64Credentials = authHeader.substring(SecurityContants.BASIC_TOKEN_PREFIX.length()).trim();
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			String credentials = new String(credDecoded, StandardCharsets.UTF_8);
			// credentials = username:password
			final String[] values = credentials.split(":", 2);
			return new UsernamePasswordAuthenticationToken(values[0], values[1], new ArrayList<>());
		}
		return null;
	}
}
