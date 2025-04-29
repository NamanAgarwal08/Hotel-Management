package com.hotelmanagement.microservices.room.service;


import com.hotelmanagement.microservices.room.dto.BookingDTO;
import com.hotelmanagement.microservices.room.dto.RoomDTO;
import com.hotelmanagement.microservices.room.entity.BookingEntity;
import com.hotelmanagement.microservices.room.entity.RoomEntity;
import com.hotelmanagement.microservices.room.repository.BookingRepository;
import com.hotelmanagement.microservices.room.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService implements RoomServiceInterface {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RoomDTO createRoom(RoomDTO roomDTO) {
        RoomEntity roomEntity = modelMapper.map(roomDTO, RoomEntity.class);
        RoomEntity savedEntity = roomRepository.save(roomEntity);
        return modelMapper.map(savedEntity, RoomDTO.class);
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findAll().stream().map(room -> modelMapper.map(room, RoomDTO.class)).toList();
    }

    @Override
    public RoomDTO getRoomByNumber(Integer roomNumber) {
        RoomEntity foundRoom = roomRepository.findByRoomNumber(roomNumber);
        if(foundRoom == null){
            throw new RuntimeException("No room found with the provided room number: " + roomNumber);
        }

        return modelMapper.map(foundRoom, RoomDTO.class);
    }

    @Override
    public RoomDTO updateRoomDetails(Integer roomNumber, RoomDTO newRoomDTO) {
        RoomEntity foundRoom = roomRepository.findByRoomNumber(roomNumber);
        if(foundRoom == null){
            throw new RuntimeException("No room found with the provided room number: " + roomNumber);
        }

        modelMapper.map(newRoomDTO, foundRoom);
        roomRepository.save(foundRoom);

        return modelMapper.map(foundRoom, RoomDTO.class);
    }

    @Override
    public String deleteRoom(Integer roomNumber) {
        RoomEntity foundRoom = roomRepository.findByRoomNumber(roomNumber);
        if(foundRoom == null){
            throw new RuntimeException("No room found with the provided room number: " + roomNumber);
        }

        roomRepository.delete(foundRoom);

        return "Room details of room number " + roomNumber + " deleted";
    }

    @Override
    public List<RoomDTO> getAvailableRooms(String checkInDate, String checkOutDate) {
        return roomRepository.findAvailableRoomsBetweenDates(checkInDate, checkOutDate).stream().map(room -> modelMapper.map(room, RoomDTO.class)).toList();
    }

    @Override
    public String bookRooms(BookingDTO bookingDTO) {
        List<Integer> roomNumbers = bookingDTO.getRoomNumbers();
        for (Integer roomNumber : roomNumbers) {
            BookingEntity bookingEntity = modelMapper.map(bookingDTO, BookingEntity.class);
            bookingEntity.setRoomNumber(roomNumber);
            bookingRepository.save(bookingEntity);
        }
        return "Bookings Confirmed!";
    }

}