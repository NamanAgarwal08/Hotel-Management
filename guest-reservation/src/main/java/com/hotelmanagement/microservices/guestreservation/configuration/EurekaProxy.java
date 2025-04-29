package com.hotelmanagement.microservices.guestreservation.configuration;

import com.hotelmanagement.microservices.guestreservation.dto.BookingDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "room")
public interface EurekaProxy {

    @PostMapping("/rooms/book")
    public String bookRooms(@RequestBody BookingDTO bookingDTO);
}
