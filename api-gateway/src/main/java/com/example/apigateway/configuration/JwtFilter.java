package com.example.apigateway.configuration;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.example.apigateway.entity.Role;
import com.example.apigateway.service.JwtService;

import jakarta.ws.rs.core.HttpHeaders;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements WebFilter, Ordered{
	
	@Autowired 
	private JwtService jwtService;
	

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		// TODO Auto-generated method stub
		ServerHttpRequest request = exchange.getRequest();
	
		final String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		
		final String jwt;
		final String userEmail;
		
		if(authHeader == null || !authHeader.startsWith("Bearer")) {
			return chain.filter(exchange);
			
		}

		jwt = authHeader.substring(7);

		userEmail = jwtService.extractUserName(jwt);
		
		
		if(userEmail != null) {
			
			//before token validation
			Role role = null;
			
			if(request.getURI().getPath().contains("admin")) 
				role = Role.ADMIN;
			
			else if(request.getURI().getPath().contains("manager") || request.getURI().getPath().contains("staff") || request.getURI().getPath().contains("rooms")) {
//				System.out.println("Here in manager!");
				if (request.getURI().getPath().contains("rooms/get") || request.getURI().getPath().contains("rooms/available"))
					role = Role.RECEPTIONIST;
				else role = Role.MANAGER;

//				System.out.println(role);
			}
			else
				role = Role.RECEPTIONIST;
				
			if(jwtService.isTokenValid(jwt, role)) {
				
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEmail, null, List.of(new SimpleGrantedAuthority(role.name())));
				
				return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authToken));
			}
			
		}
		
		return chain.filter(exchange);
	}



	@Override
	public int getOrder() {
		
		return -1;	//this negative value ensures that it will run before spring security filter
	}

}
	
