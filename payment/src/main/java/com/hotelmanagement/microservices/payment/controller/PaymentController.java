package com.hotelmanagement.microservices.payment.controller;


import com.hotelmanagement.microservices.payment.dto.ReservationDetails;
import com.hotelmanagement.microservices.payment.dto.StripeResponse;
import com.hotelmanagement.microservices.payment.servcice.StripeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    StripeServiceInterface stripeServiceInterface;

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> makePayment(@RequestBody ReservationDetails reservationDetails){
        return ResponseEntity.ok(stripeServiceInterface.makePayment(reservationDetails));
    }

    @GetMapping("/success")
    public String successMsg(){
        return "Payment Successful";
    }

    @GetMapping("/cancel")
    public String cancelMsg(){
        return "Payment Unsuccessful";
    }

}
