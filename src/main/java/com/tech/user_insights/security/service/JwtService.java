package com.tech.user_insights.security.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
	
	public String generateToken(String userName);

	public String extractUserName(String token);

	public Boolean validateToken(String token, UserDetails userDetails);

}
