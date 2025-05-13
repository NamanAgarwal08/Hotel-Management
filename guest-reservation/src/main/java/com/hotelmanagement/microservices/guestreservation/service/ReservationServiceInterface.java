package com.hotelmanagement.microservices.guestreservation.service;


import com.hotelmanagement.microservices.guestreservation.dto.BookingDTO;
import com.hotelmanagement.microservices.guestreservation.dto.ReservationDTO;
import com.hotelmanagement.microservices.guestreservation.dto.StripeResponse;
import com.hotelmanagement.microservices.guestreservation.exception.RoomNotAvailableException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReservationServiceInterface {
    ResponseEntity<StripeResponse> createReservation(ReservationDTO reservation) throws RoomNotAvailableException;

    List<ReservationDTO> getAllReservations();

    ReservationDTO getReservations(Long id);

    ReservationDTO updateReservationDetails(Long id, ReservationDTO reservationDTO);

    String deleteReservation(Long id);

    List<Long> bookRooms(BookingDTO bookingDTO);
}
