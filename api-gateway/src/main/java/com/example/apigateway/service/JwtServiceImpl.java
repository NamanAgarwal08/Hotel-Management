package com.example.apigateway.service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.example.apigateway.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService{

	private String secretKey = "1he9ohlYTfz3Nh3vQFd8GbkGKw0uxggcGoY5f4j4oXY=";
	
	
	public Key getSiginKey() {
		byte[] key = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(key);
	}

	public String extractUserName(String token) {
	
		return extractClaim(token, Claims::getSubject);
		
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
		final Claims claims = extractAllClaims(token);
		return claimsResolvers.apply(claims);
	}
	
	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				   .setSigningKey(getSiginKey())
				   .build()
				   .parseClaimsJws(token).getBody();
	}
	
	public boolean isTokenValid(String token, Role role) {
		
		Claims claims = extractAllClaims(token);	
		
//		System.out.println( "Role is " + (String) claims.get("userRole"));
		
		return ( (String) claims.get("userRole")).length() <= role.name().length() && !isTokenExpired(token) ;
		
		
	}
	
	public boolean isTokenExpired(String token) {

		boolean result = extractClaim(token, Claims::getExpiration).before(new Date());
		 if(result){
			 System.out.println("Expire ho gya");
			throw new RuntimeException("JWTJWT");
		 }

		 return result;
	}

}