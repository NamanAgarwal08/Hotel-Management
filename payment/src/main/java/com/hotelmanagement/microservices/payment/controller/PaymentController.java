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

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> makePayment(@Valid @RequestBody ReservationDetails reservationDetails){
        return ResponseEntity.ok(stripeServiceInterface.makePayment(reservationDetails));
    }

    @GetMapping("/success")
    public PaymentEntity successMsg(@RequestParam String sessionId){
        PaymentEntity paymentEntity = paymentRepository.findBySessionId(sessionId);

        paymentEntity.setStatus("SUCCESS");
        paymentRepository.save(paymentEntity);

        guestReservationProxy.changeStatus(sessionId, "SUCCESS");

        return paymentEntity;
    }

    @GetMapping("/cancel")
    public PaymentEntity cancelMsg(@RequestParam String sessionId){
        PaymentEntity paymentEntity = paymentRepository.findBySessionId(sessionId);

        paymentEntity.setStatus("CANCELLED");
        paymentRepository.save(paymentEntity);

        guestReservationProxy.changeStatus(sessionId, "CANCELED");

        return paymentEntity;
    }

}
