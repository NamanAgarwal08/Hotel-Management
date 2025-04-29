package com.hotelmanagement.microservices.room.controller;

import com.hotelmanagement.microservices.room.dto.BookingDTO;
import com.hotelmanagement.microservices.room.dto.RoomDTO;
import com.hotelmanagement.microservices.room.service.RoomServiceInterface;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomServiceInterface roomServiceInterface;

    @PostMapping
    public RoomDTO createRoom(@Valid @RequestBody RoomDTO roomDTO){
        return roomServiceInterface.createRoom(roomDTO);
    }

    @GetMapping
    public List<RoomDTO> getAllRooms(){
        return roomServiceInterface.getAllRooms();
    }

    @GetMapping("/{roomNumber}")
    public RoomDTO getRoomByNumber(@PathVariable Integer roomNumber){
        return roomServiceInterface.getRoomByNumber(roomNumber);
    }

    @PatchMapping("/{roomNumber}")
    public RoomDTO updateRoomDetails(@PathVariable Integer roomNumber, @Valid @RequestBody RoomDTO newRoomDTO){
        return roomServiceInterface.updateRoomDetails(roomNumber, newRoomDTO);
    }

    @DeleteMapping("/{roomNumber}")
    public String deleteRoom(@PathVariable Integer roomNumber){
        return roomServiceInterface.deleteRoom(roomNumber);
    }

    @GetMapping("/available")
    public List<RoomDTO> getAvailableRooms(@RequestParam String checkInDate, @RequestParam String checkOutDate){
        return roomServiceInterface.getAvailableRooms(checkInDate, checkOutDate);
    }

    @PostMapping("/book")
    public String bookRooms(@RequestBody BookingDTO bookingDTO){
        return roomServiceInterface.bookRooms(bookingDTO);
    }

}
