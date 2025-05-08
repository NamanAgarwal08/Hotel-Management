package com.example.authservice.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.example.authservice.entity.UserEntity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService{
	
	String secretKey = "1he9ohlYTfz3Nh3vQFd8GbkGKw0uxggcGoY5f4j4oXY=";
	
	public String generateToken(UserEntity userDetails) {
		
		HashMap<String, String> claims = new HashMap<>();
		claims.put("userRole", userDetails.getRole().name());
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userDetails.getEmail())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*60))
				.signWith(getSiginKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public Key getSiginKey() {
		byte[] key = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(key);
	}

}