package com.hotelmanagement.microservices.guestreservation.config;

import com.hotelmanagement.microservices.guestreservation.dto.ApiResponse;
import com.hotelmanagement.microservices.guestreservation.dto.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "room")
public interface EurekaProxy {

    @PostMapping("/rooms/book")
    public ResponseEntity<ApiResponse<String>> bookRooms(@RequestBody BookingDTO bookingDTO);
}
