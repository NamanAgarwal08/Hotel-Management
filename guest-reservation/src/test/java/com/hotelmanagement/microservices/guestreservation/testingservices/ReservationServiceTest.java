package com.hotelmanagement.microservices.guestreservation.testingservices;

import com.hotelmanagement.microservices.guestreservation.configuration.EurekaProxy;
import com.hotelmanagement.microservices.guestreservation.dto.BookingDTO;
import com.hotelmanagement.microservices.guestreservation.dto.ReservationDTO;
import com.hotelmanagement.microservices.guestreservation.entity.ReservationEntity;
import com.hotelmanagement.microservices.guestreservation.repository.ReservationRepository;
import com.hotelmanagement.microservices.guestreservation.service.ReservationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private EurekaProxy eurekaProxy;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void testCreateReservation() {
        ReservationDTO reservationDTO = new ReservationDTO();
        ReservationEntity reservationEntity = new ReservationEntity();
        Mockito.when(modelMapper.map(reservationDTO, ReservationEntity.class)).thenReturn(reservationEntity);
        Mockito.when(reservationRepository.save(reservationEntity)).thenReturn(reservationEntity);
        Mockito.when(modelMapper.map(reservationEntity, ReservationDTO.class)).thenReturn(reservationDTO);
        ReservationDTO result = reservationService.createReservation(reservationDTO);
        Assertions.assertNotNull(result);
    }


    @Test
    void testGetReservation() {
        ReservationEntity reservationEntity = new ReservationEntity();
        ReservationDTO reservationDTO = new ReservationDTO();
        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservationEntity));
        Mockito.when(modelMapper.map(reservationEntity, ReservationDTO.class)).thenReturn(reservationDTO);
        ReservationDTO result = reservationService.getReservations(1L);
        Assertions.assertNotNull(result);
    }


    @Test
    void testDeleteReservation() {
        ReservationEntity reservationEntity = new ReservationEntity();

        Mockito.when(reservationRepository.findById(1L)).thenReturn(Optional.of(reservationEntity));
        String result = reservationService.deleteReservation(1L);
        Assertions.assertEquals("Reservation details deleted successfully!", result);
    }


    @Test
    void testBookRooms() {
        BookingDTO bookingDTO = new BookingDTO();

        Mockito.when(eurekaProxy.bookRooms(bookingDTO)).thenReturn("Success");
        String result = reservationService.bookRooms(bookingDTO);
        Assertions.assertEquals("Success", result);
    }
}
