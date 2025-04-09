package com.tech.user_insights.security.serviceImpl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.tech.user_insights.security.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtServiceImpl implements JwtService {

	public static final long JWT_TOKEN_VALIDITY = 1 * 60 * 60;
	public static final long JWT_REFRESH_TOKEN_VALIDITY = 6 * 60 * 60;
	private static final String secret = "jwtTokenKey2144543545344134543535451345454351345451435234561435562562561556536464565464564564534656567567678768";

	@Override
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userDetails);
	}

	private String doGenerateToken(Map<String, Object> claims, UserDetails userDetails) {
		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
				.claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	@Override
	public String extractUserName(String token) {
		return extractClaimes(token, Claims::getSubject);
	}

	private <T> T extractClaimes(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = extractExpiration(token);
		boolean before = expiration.before(new Date());
		return before;
	}

	public Date extractExpiration(String token) {
		return extractClaimes(token, Claims::getExpiration);
	}

	public Boolean validateToken(String token, UserDetails userDetails) {

		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));

	}

}
