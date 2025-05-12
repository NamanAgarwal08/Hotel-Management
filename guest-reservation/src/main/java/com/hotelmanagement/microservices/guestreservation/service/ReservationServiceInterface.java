package com.hotelmanagement.microservices.guestreservation.service;


import com.hotelmanagement.microservices.guestreservation.dto.BookingDTO;
import com.hotelmanagement.microservices.guestreservation.dto.ReservationDTO;
import com.hotelmanagement.microservices.guestreservation.exception.RoomNotAvailableException;

import java.util.List;

public interface ReservationServiceInterface {
    ReservationDTO createReservation(ReservationDTO reservation) throws RoomNotAvailableException;

    List<ReservationDTO> getAllReservations();

    ReservationDTO getReservations(Long id);

    ReservationDTO updateReservationDetails(Long id, ReservationDTO reservationDTO);

    String deleteReservation(Long id);

    String bookRooms(BookingDTO bookingDTO);
}
