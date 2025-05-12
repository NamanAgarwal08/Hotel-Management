package com.example.apigateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApiGatewayConfig {
	
	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
					  .route(p -> p.path("/reservations/**")
							  	   .uri("lb://GUEST-RESERVATION"))
					  .route(p -> p.path("/guests/**")
							  	   .uri("lb://GUEST-RESERVATION"))
					  .route(p -> p.path("/rooms/**")
							  	   .uri("lb://ROOM"))
					  .route(p -> p.path("/auth/**")
							       .uri("lb://AUTH-SERVICE"))
					  .route(p -> p.path("/staff/**")
							       .uri("lb://STAFF"))
				      .route(p -> p.path("/payment/**")
						           .uri("lb://PAYMENT"))
					  .build();
					  
	}
	
}
