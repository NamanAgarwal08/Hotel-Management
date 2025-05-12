package com.hotelmanagement.microservices.guestreservation.controller;


import com.hotelmanagement.microservices.guestreservation.dto.ApiResponse;
import com.hotelmanagement.microservices.guestreservation.dto.BookingDTO;
import com.hotelmanagement.microservices.guestreservation.dto.ReservationDTO;
import com.hotelmanagement.microservices.guestreservation.exception.RoomNotAvailableException;
import com.hotelmanagement.microservices.guestreservation.service.ReservationServiceInterface;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private static final String SERVICE_NAME = "ReservationService";

    @Autowired
    private ReservationServiceInterface reservationServiceInterface;

    @PostMapping
    @CircuitBreaker(name = SERVICE_NAME, fallbackMethod = "fallbackResponse")
    public ResponseEntity<ApiResponse<ReservationDTO>> createReservation(@Valid @RequestBody ReservationDTO reservation) throws RoomNotAvailableException {
        ReservationDTO responseDTO = reservationServiceInterface.createReservation(reservation);
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservations details added successfully!", responseDTO));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReservationDTO>>> getAllReservations(){
        List<ReservationDTO> reservationList =  reservationServiceInterface.getAllReservations();
        return ResponseEntity.ok(new ApiResponse<>(true, "List fetched from database!", reservationList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservationDTO>> getReservation(@PathVariable Long id){
        ReservationDTO responseDTO = reservationServiceInterface.getReservations(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservation entity found!", responseDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ReservationDTO>> updateReservationDetails(@PathVariable Long id, @Valid @RequestBody ReservationDTO reservationDTO){
        ReservationDTO updatedDTO = reservationServiceInterface.updateReservationDetails(id, reservationDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservation details updated successfully!", updatedDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteReservation(@PathVariable Long id){
        String responseData = reservationServiceInterface.deleteReservation(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Reservation details deleted successfully!", responseData));
    }

    @PostMapping("/book")
    @CircuitBreaker(name = SERVICE_NAME, fallbackMethod = "fallbackResponse")
    public ResponseEntity<ApiResponse<String>> bookRooms(@Valid @RequestBody BookingDTO bookingDTO){
        String responseData = reservationServiceInterface.bookRooms(bookingDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking successful!", responseData));
    }

    public ResponseEntity<ApiResponse<String>> fallbackResponse(FeignException ex) {
        return new ResponseEntity<>(new ApiResponse<>(false, "Room Service is temporarily down!", null), HttpStatus.BAD_GATEWAY);
    }

}