package com.hotelmanagement.microservices.guestreservation;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GuestReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuestReservationApplication.class, args);
	}


}
