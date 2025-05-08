package com.example.authservice.service;

import com.example.authservice.exception.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.authservice.dto.LoginDto;
import com.example.authservice.dto.UserDto;
import com.example.authservice.entity.Role;
import com.example.authservice.entity.UserEntity;
import com.example.authservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtService jwtService;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	public UserDto register(UserDto user, Role role) throws UserAlreadyExistException {

		UserEntity foundEntity = userRepository.findByEmail(user.getEmail());
		if(foundEntity!=null){
			throw new UserAlreadyExistException("User with email-id " + user.getEmail() + " already exists!");
		}

		UserEntity newUser = new UserEntity(user.getFirstName(), user.getLastName(), user.getEmail(), encoder.encode(user.getPassword()));
		
		newUser.setRole(role);
		
		newUser = userRepository.save(newUser);
		
		UserDto resDto = new UserDto(newUser.getId(), newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(), user.getPassword(), newUser.getRole());
		
		return resDto;
		
	}
	
	public String login(LoginDto loginDto) {
		
		UserEntity foundUser = userRepository.findByEmail(loginDto.getEmail());
		
		
		String token = jwtService.generateToken(foundUser);
		
		return "Token : "+token;
		
	}

}
