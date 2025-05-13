package com.hotelmanagement.microservices.room.service;


import com.hotelmanagement.microservices.room.dto.BookingDTO;
import com.hotelmanagement.microservices.room.dto.RoomDTO;
import com.hotelmanagement.microservices.room.entity.BookingEntity;
import com.hotelmanagement.microservices.room.entity.RoomEntity;
import com.hotelmanagement.microservices.room.exception.ResourceNotFoundException;
import com.hotelmanagement.microservices.room.exception.RoomNotAvailableException;
import com.hotelmanagement.microservices.room.repository.BookingRepository;
import com.hotelmanagement.microservices.room.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        RoomEntity foundRoom = roomRepository.findByRoomNumber(roomNumber).orElseThrow(() -> new ResourceNotFoundException("No room found with the provided room number: " + roomNumber));

        return modelMapper.map(foundRoom, RoomDTO.class);
    }

    @Override
    public RoomDTO updateRoomDetails(Integer roomNumber, RoomDTO newRoomDTO) {
        RoomEntity foundRoom = roomRepository.findByRoomNumber(roomNumber).orElseThrow(() -> new ResourceNotFoundException("No room found with the provided room number: " + roomNumber));

        modelMapper.map(newRoomDTO, foundRoom);
        roomRepository.save(foundRoom);

        return modelMapper.map(foundRoom, RoomDTO.class);
    }

    @Override
    public String deleteRoom(Integer roomNumber) {
        RoomEntity foundRoom = roomRepository.findByRoomNumber(roomNumber).orElseThrow(() -> new ResourceNotFoundException("No room found with the provided room number: " + roomNumber));

        roomRepository.delete(foundRoom);

        return "Room details of room number " + roomNumber + " deleted";
    }

    @Override
    public List<RoomDTO> getAvailableRooms(String checkInDate, String checkOutDate) {
        return roomRepository.findAvailableRoomsBetweenDates(checkInDate, checkOutDate).stream().map(room -> modelMapper.map(room, RoomDTO.class)).toList();
    }

    @Override
    public List<Long> bookRooms(BookingDTO bookingDTO) throws RoomNotAvailableException {
        List<Integer> roomNumbers = bookingDTO.getRoomNumbers();
        Set<Integer> availableRooms = getAvailableRooms(bookingDTO.getCheckInDate(), bookingDTO.getCheckOutDate()).stream().map(RoomDTO::getRoomNumber).collect(Collectors.toSet());
        List<Long> bookingIds = new ArrayList<>();
        for (Integer roomNumber : roomNumbers) {
            if(!availableRooms.contains(roomNumber)){
                throw new RoomNotAvailableException("Specified room(s) not available for provided checkIn and checkOut dates!");
            }
        }
        Long amount = 0L;
        for(int i=0;i<roomNumbers.size();i++){
            amount+=getRoomByNumber(roomNumbers.get(i)).getAmount();
        }

        for (Integer roomNumber : roomNumbers) {
            BookingEntity bookingEntity = modelMapper.map(bookingDTO, BookingEntity.class);
            bookingEntity.setRoomNumber(roomNumber);
            bookingEntity.setStatus("PENDING");
            BookingEntity bookedEntity = bookingRepository.save(bookingEntity);
            bookingIds.add(bookedEntity.getId());
        }
        bookingIds.add(amount); // last index is of amount
        return bookingIds;
    }

}