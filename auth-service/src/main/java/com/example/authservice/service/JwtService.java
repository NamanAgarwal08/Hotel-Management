package com.example.authservice.service;

import java.security.Key;

import com.example.authservice.entity.UserEntity;

public interface JwtService {
	
	
	public String generateToken(UserEntity userDetails);
	
	public Key getSiginKey();
	
	

	
	
}
