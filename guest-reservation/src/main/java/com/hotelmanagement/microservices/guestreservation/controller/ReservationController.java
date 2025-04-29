package com.hotelmanagement.microservices.guestreservation.controller;


import com.hotelmanagement.microservices.guestreservation.dto.BookingDTO;
import com.hotelmanagement.microservices.guestreservation.dto.ReservationDTO;
import com.hotelmanagement.microservices.guestreservation.service.ReservationServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationServiceInterface reservationServiceInterface;

    @PostMapping
    public ReservationDTO createReservation(@Valid @RequestBody ReservationDTO reservation){
        return reservationServiceInterface.createReservation(reservation);
    }

    @GetMapping
    public List<ReservationDTO> getAllReservations(){
        return reservationServiceInterface.getAllReservations();
    }

    @GetMapping("/{id}")
    public ReservationDTO getReservation(@PathVariable Long id){
        return reservationServiceInterface.getReservations(id);
    }

    @PatchMapping("/{id}")
    public ReservationDTO updateReservationDetails(@PathVariable Long id, @Valid @RequestBody ReservationDTO reservationDTO){
        return reservationServiceInterface.updateReservationDetails(id, reservationDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteReservation(@PathVariable Long id){
        return reservationServiceInterface.deleteReservation(id);
    }

    @PostMapping("/book")
    public String bookRooms(@RequestBody BookingDTO bookingDTO){
        return reservationServiceInterface.bookRooms(bookingDTO);
    }
}
