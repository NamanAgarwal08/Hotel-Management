package com.hotelmanagement.microservices.guestreservation.servicetest;

import com.hotelmanagement.microservices.guestreservation.dto.GuestDTO;
import com.hotelmanagement.microservices.guestreservation.service.GuestService;
import com.hotelmanagement.microservices.guestreservation.entity.GuestEntity;
import com.hotelmanagement.microservices.guestreservation.repository.GuestRepository;
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
public class GuestServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private GuestService guestService;

    @Test
    void testAddGuest() {
        GuestDTO guestDTO = new GuestDTO();
        GuestEntity guestEntity = new GuestEntity();

        Mockito.when(modelMapper.map(guestDTO, GuestEntity.class)).thenReturn(guestEntity);
        Mockito.when(guestRepository.save(guestEntity)).thenReturn(guestEntity);
        Mockito.when(modelMapper.map(guestEntity, GuestDTO.class)).thenReturn(guestDTO);
        GuestDTO savedGuest = guestService.addGuest(guestDTO);
        Assertions.assertNotNull(savedGuest);
    }


    @Test
    void testGetGuest() {
        GuestEntity guestEntity = new GuestEntity();
        GuestDTO guestDTO = new GuestDTO();
        Mockito.when(guestRepository.findById(1L)).thenReturn(Optional.of(guestEntity));
        Mockito.when(modelMapper.map(guestEntity, GuestDTO.class)).thenReturn(guestDTO);
        GuestDTO result = guestService.getGuest(1L);
        Assertions.assertNotNull(result);
    }

    @Test
    void testDeleteGuest() {
        GuestEntity guestEntity = new GuestEntity();

        Mockito.when(guestRepository.findById(1L)).thenReturn(Optional.of(guestEntity));

        String result = guestService.deleteGuest(1L);
        Assertions.assertEquals("Guest details deleted successfully!", result);
    }
}
