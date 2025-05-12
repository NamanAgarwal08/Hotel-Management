package com.example.authservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.authservice.entity.Role;
import com.example.authservice.entity.UserEntity;
import com.example.authservice.repository.UserRepository;

@SpringBootApplication
public class AuthServiceApplication implements CommandLineRunner{
	
	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		if(userRepository.findByEmail("admin@gmail.com") == null) {
			
			UserEntity admin = new UserEntity();
			
			admin.setFirstName("admin");
			admin.setLastName("admin");
			admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
			admin.setEmail("admin@gmail.com");
			admin.setRole(Role.ADMIN);
			
			userRepository.save(admin);
			
		}
		
	}
	
	

}
