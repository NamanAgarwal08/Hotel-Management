package com.hotelmanagement.microservices.guestreservation.service;


import com.hotelmanagement.microservices.guestreservation.dto.ReservationDTO;
import com.hotelmanagement.microservices.guestreservation.dto.RoomDTO;
import com.hotelmanagement.microservices.guestreservation.dto.StripeResponse;
import com.hotelmanagement.microservices.guestreservation.exception.FeignServiceUnavailableException;
import com.hotelmanagement.microservices.guestreservation.exception.RoomNotAvailableException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReservationServiceInterface {
    StripeResponse createReservation(ReservationDTO reservation) throws RoomNotAvailableException, FeignServiceUnavailableException;

    List<ReservationDTO> getAllReservations();

    ReservationDTO getReservations(Long id);

    ReservationDTO updateReservationDetails(Long id, ReservationDTO reservationDTO);

    String deleteReservation(Long id);

    List<RoomDTO> checkAvailability(String checkInDate, String checkOutDate) throws FeignServiceUnavailableException;
}
