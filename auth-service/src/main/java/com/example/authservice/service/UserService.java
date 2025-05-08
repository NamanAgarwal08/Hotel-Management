package com.example.authservice.service;

import com.example.authservice.dto.LoginDto;
import com.example.authservice.dto.UserDto;
import com.example.authservice.entity.Role;
import com.example.authservice.exception.UserAlreadyExistException;

public interface UserService {
	
	public UserDto register(UserDto user, Role role) throws UserAlreadyExistException;
	
	public String login(LoginDto loginDto);

}
