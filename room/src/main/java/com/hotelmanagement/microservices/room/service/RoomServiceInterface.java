package com.hotelmanagement.microservices.room.service;

import com.hotelmanagement.microservices.room.dto.BookingDTO;
import com.hotelmanagement.microservices.room.dto.RoomDTO;

import java.util.List;

public interface RoomServiceInterface {
    RoomDTO createRoom(RoomDTO roomDTO);

    List<RoomDTO> getAllRooms();

    RoomDTO getRoomByNumber(Integer roomNumber);

    RoomDTO updateRoomDetails(Integer roomNumber, RoomDTO newRoomDTO);

    String deleteRoom(Integer roomNumber);

    List<RoomDTO> getAvailableRooms(String checkInDate, String checkOutDate);

    String bookRooms(BookingDTO bookingDTO);
}
