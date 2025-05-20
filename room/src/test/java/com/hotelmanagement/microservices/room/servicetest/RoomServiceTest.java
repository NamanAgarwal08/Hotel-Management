package com.hotelmanagement.microservices.room.servicetest;

import com.hotelmanagement.microservices.room.dto.RoomDTO;
import com.hotelmanagement.microservices.room.entity.RoomEntity;
import com.hotelmanagement.microservices.room.exception.RoomNotAvailableException;
import com.hotelmanagement.microservices.room.repository.RoomRepository;
import com.hotelmanagement.microservices.room.service.RoomService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;


    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RoomService roomService;

    @Test
    void testCreateRoom() {
        RoomDTO roomDTO = new RoomDTO();
        RoomEntity roomEntity = new RoomEntity();
        Mockito.when(modelMapper.map(roomDTO, RoomEntity.class)).thenReturn(roomEntity);
        Mockito.when(roomRepository.save(roomEntity)).thenReturn(roomEntity);
        Mockito.when(modelMapper.map(roomEntity, RoomDTO.class)).thenReturn(roomDTO);
        RoomDTO result = roomService.createRoom(roomDTO);
        Assertions.assertNotNull(result);
    }

    @Test
    void testGetRoomByNumber() {
        RoomEntity roomEntity = new RoomEntity();
        RoomDTO roomDTO = new RoomDTO();
        Mockito.when(roomRepository.findByRoomNumber(101)).thenReturn(Optional.of(roomEntity));
        Mockito.when(modelMapper.map(roomEntity, RoomDTO.class)).thenReturn(roomDTO);
        RoomDTO result = roomService.getRoomByNumber(101);
        Assertions.assertNotNull(result);
    }

    @Test
    void testDeleteRoom() {
        RoomEntity roomEntity = new RoomEntity();
        Mockito.when(roomRepository.findByRoomNumber(101)).thenReturn(Optional.of(roomEntity));
        String result = roomService.deleteRoom(101);
        Assertions.assertEquals("Room details of room number 101 deleted", result);
    }

}

