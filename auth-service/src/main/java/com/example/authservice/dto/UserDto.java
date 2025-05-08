package com.example.authservice.dto;

import com.example.authservice.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private Long id;

	@NotNull(message = "First name required!")
	private String firstName;
	private String lastName;

	@NotNull(message = "Please provide email!")
	@Email(message = "Enter email in correct format!")
	private String email;

	@NotNull(message = "Please provide password!")
	private String password;
	
	private Role role;

	public UserDto(String firstName, String lastName, String email, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
}