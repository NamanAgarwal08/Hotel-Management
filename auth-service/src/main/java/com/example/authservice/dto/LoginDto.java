package com.example.authservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

	@NotNull(message = "Please provide email!")
	private String email;

	@NotNull(message = "Please provide password!")
	private String password;
	
}
