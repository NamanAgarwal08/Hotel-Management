package com.hotelmanagement.microservices.payment.controller;


import com.hotelmanagement.microservices.payment.config.GuestReservationProxy;
import com.hotelmanagement.microservices.payment.dto.ReservationDetails;
import com.hotelmanagement.microservices.payment.dto.StripeResponse;
import com.hotelmanagement.microservices.payment.entity.PaymentEntity;
import com.hotelmanagement.microservices.payment.repository.PaymentRepository;
import com.hotelmanagement.microservices.payment.servcice.StripeServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    StripeServiceInterface stripeServiceInterface;

    @Autowired
    GuestReservationProxy guestReservationProxy;

    @PostMapping("/checkout/{id}")
    public ResponseEntity<StripeResponse> makePayment(@Valid @RequestBody ReservationDetails reservationDetails, @PathVariable Long id){
        return ResponseEntity.ok(stripeServiceInterface.makePayment(reservationDetails, id));
    }

    @GetMapping("/success")
    public PaymentEntity successMsg(@RequestParam String sessionId, @RequestParam Long id){
        PaymentEntity paymentEntity = paymentRepository.findBySessionId(sessionId);

        guestReservationProxy.changeStatus(id);

        paymentEntity.setStatus("SUCCESS");
        return paymentEntity;
    }

    @GetMapping("/cancel")
    public String cancelMsg(){
        return "Payment Unsuccessful";
    }


}
