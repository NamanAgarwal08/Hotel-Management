package com.hotelmanagement.microservices.payment.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "guest-reservation")
public interface GuestReservationProxy {
    @PostMapping("/reservations/changestatus/{sessionId}/{status}")
    public void changeStatus(@PathVariable String sessionId, @PathVariable String status);
}
