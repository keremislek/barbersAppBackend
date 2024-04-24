package com.example.barbersApp.service.impl;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	private static final String SECRET_KEY = "c079db160fe33550f86bb32f854539c8650bf4a3e9cc86859e6d56f5ab67eb15";

	public String extractUsername(String token) {
		
		return extractClaim(token, Claims::getSubject);
	}
	 public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }
	 
	 public String generateToken(UserDetails userDetails) {
		 return generateToken(new HashMap<>(),userDetails);
	 }
	 
	  
	public String generateToken(Map<String, Object> extraClaims,UserDetails userDetails) 
	{
		extraClaims.put("role", userDetails.getAuthorities());
		

		return Jwts.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+100000 * 60 * 24 * 365))
				.signWith(getSignInKey(),SignatureAlgorithm.HS256).compact();
				
	}

	public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	
	private boolean isTokenExpired(String token) {

		return extractExpiration(token).before(new Date());
	}
	private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

	private Claims extractAllClaims(String token) {
		return Jwts.parser()
			.setSigningKey(getSignInKey())
			.parseClaimsJws(token)
			.getBody();
	}
	

	private Key getSignInKey() {
		 byte[] key = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(key);
	}
}
