package com.example.apigateway.configuration;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;


@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean 
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) throws Exception{
		return http.csrf(customizer -> customizer.disable())
				.authorizeExchange(exchange -> exchange.pathMatchers("/auth/login")
						.permitAll()
						.anyExchange()
						.authenticated())
				.addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
				.build();

	}

}
