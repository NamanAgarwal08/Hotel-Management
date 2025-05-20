package com.hotelmanagement.microservices.guestreservation.config;

import com.hotelmanagement.microservices.guestreservation.dto.ReservationDetailsPayment;
import com.hotelmanagement.microservices.guestreservation.dto.StripeResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "payment")
public interface PaymentServiceProxy {

    @PostMapping("/payment/checkout")
    public ResponseEntity<StripeResponse> makePayment(@Valid @RequestBody ReservationDetailsPayment reservationDetails);

}
