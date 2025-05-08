package com.example.authservice.controller;

import com.example.authservice.exception.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.authservice.dto.LoginDto;
import com.example.authservice.dto.UserDto;
import com.example.authservice.entity.Role;
import com.example.authservice.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/admin/register/{role}")
	public UserDto registerAdmin(@RequestBody UserDto user, @PathVariable String role) throws UserAlreadyExistException {
		
		Role foundRole = null;
		
		if(role.toUpperCase().equals("ADMIN"))
			foundRole = Role.ADMIN;
		
		else if(role.toUpperCase().equals("MANAGER"))
			foundRole = Role.MANAGER;
		
		else
			foundRole = Role.RECEPTIONIST;
		
		
		return userService.register(user, foundRole);
		
	}
	
	
	@PostMapping("/manager/register/receptionist")
	public UserDto registerManager(@RequestBody UserDto user) throws UserAlreadyExistException {
		
		return userService.register(user, Role.RECEPTIONIST);
	}
	
	@PostMapping("/login")
	public String loginManager(@RequestBody LoginDto loginDto) {
		
		//authentication through authentication manager
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		
		return userService.login(loginDto);
	}
	
	

}
