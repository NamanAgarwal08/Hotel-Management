package com.hotelmanagement.microservices.guestreservation.service;

import com.hotelmanagement.microservices.guestreservation.config.RoomServiceProxy;
import com.hotelmanagement.microservices.guestreservation.dto.BookingDTO;
import com.hotelmanagement.microservices.guestreservation.dto.ReservationDTO;
import com.hotelmanagement.microservices.guestreservation.entity.ReservationEntity;
import com.hotelmanagement.microservices.guestreservation.exception.ResourceNotFoundException;
import com.hotelmanagement.microservices.guestreservation.exception.RoomNotAvailableException;
import com.hotelmanagement.microservices.guestreservation.repository.ReservationRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService implements ReservationServiceInterface{

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    RoomServiceProxy roomServiceProxy;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ReservationDTO createReservation(ReservationDTO reservation) throws RoomNotAvailableException {
        BookingDTO bookingDTO = new BookingDTO(reservation.getRoomNumbers(), reservation.getCheckInDate(), reservation.getCheckOutDate());
//        bookRooms(bookingDTO);

        if(bookRooms(bookingDTO)==null){
            throw new RoomNotAvailableException("Specified room(s) not available for the provided checkIn and checkOut dates!");
        };

        ReservationEntity reservationEntity = modelMapper.map(reservation, ReservationEntity.class);
        ReservationEntity savedReservation = reservationRepository.save(reservationEntity);
        return modelMapper.map(savedReservation, ReservationDTO.class);
    }

    @Override
    public List<ReservationDTO> getAllReservations() {
        return reservationRepository.findAll().stream().map(reservation -> modelMapper.map(reservation, ReservationDTO.class)).toList();
    }

    @Override
    public ReservationDTO getReservations(Long id) {
        ReservationEntity foundReservation = reservationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No reservation found with the provided id: " + id));
        return modelMapper.map(foundReservation, ReservationDTO.class);
    }

    @Override
    public ReservationDTO updateReservationDetails(Long id, ReservationDTO reservationDTO) {
        ReservationEntity foundReservation = reservationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No reservation found with the provided id: " + id));
        modelMapper.map(reservationDTO,foundReservation);

        reservationRepository.save(foundReservation);

        return modelMapper.map(foundReservation, ReservationDTO.class);
    }

    @Override
    public String deleteReservation(Long id) {
        ReservationEntity foundReservation = reservationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No reservation found with the provided id: " + id));

        reservationRepository.delete(foundReservation);

        return "Reservation details deleted successfully!";
    }

    @Override
    public String bookRooms(BookingDTO bookingDTO) {
        return roomServiceProxy.bookRooms(bookingDTO).getBody().getData();
    }

}