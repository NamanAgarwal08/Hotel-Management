package com.example.apigateway.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.reactive.CorsWebFilter;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private CorsWebFilter corsWebFilter;
	
	@Bean 
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http
			.csrf(ServerHttpSecurity.CsrfSpec::disable)
			.cors(cors -> cors.disable()) // CORS is handled by CorsWebFilter
			.authorizeExchange(exchange -> exchange
				.pathMatchers("/auth/login").permitAll()
				.pathMatchers("/auth/admin/register/**").hasAuthority("ADMIN")
				.pathMatchers("/auth/manager/register/**").hasAuthority("MANAGER")
				.anyExchange().authenticated()
			)
			.addFilterBefore(corsWebFilter, SecurityWebFiltersOrder.CORS)
			.addFilterAt(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
			.build();
	}

}
