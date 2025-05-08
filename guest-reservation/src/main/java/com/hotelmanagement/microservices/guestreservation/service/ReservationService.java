package com.hotelmanagement.microservices.guestreservation.service;

import com.hotelmanagement.microservices.guestreservation.config.EurekaProxy;
import com.hotelmanagement.microservices.guestreservation.dto.BookingDTO;
import com.hotelmanagement.microservices.guestreservation.dto.ReservationDTO;
import com.hotelmanagement.microservices.guestreservation.entity.ReservationEntity;
import com.hotelmanagement.microservices.guestreservation.exception.ResourceNotFoundException;
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
    EurekaProxy eurekaProxy;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ReservationDTO createReservation(ReservationDTO reservation) {
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
        return eurekaProxy.bookRooms(bookingDTO).getBody().getData();
    }


}