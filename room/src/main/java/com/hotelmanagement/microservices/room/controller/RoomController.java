package com.hotelmanagement.microservices.room.controller;

import com.hotelmanagement.microservices.room.dto.ApiResponse;
import com.hotelmanagement.microservices.room.dto.BookingDTO;
import com.hotelmanagement.microservices.room.dto.RoomDTO;
import com.hotelmanagement.microservices.room.exception.RoomNotAvailableException;
import com.hotelmanagement.microservices.room.service.RoomServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomServiceInterface roomServiceInterface;

    @PostMapping
    public ResponseEntity<ApiResponse<RoomDTO>> createRoom(@Valid @RequestBody RoomDTO roomDTO){
        RoomDTO responseDTO = roomServiceInterface.createRoom(roomDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Room entity created!", responseDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<ApiResponse<List<RoomDTO>>> getAllRooms(){
        List<RoomDTO> responseList = roomServiceInterface.getAllRooms();
        return ResponseEntity.ok(new ApiResponse<>(true, "List fetched from database!", responseList));
    }

    @GetMapping("/{roomNumber}")
    public ResponseEntity<ApiResponse<RoomDTO>> getRoomByNumber(@PathVariable Integer roomNumber){
         RoomDTO responseDTO = roomServiceInterface.getRoomByNumber(roomNumber);
         return ResponseEntity.ok(new ApiResponse<>(true, "Room entity found!", responseDTO));
    }

    @PatchMapping("/{roomNumber}")
    public ResponseEntity<ApiResponse<RoomDTO>> updateRoomDetails(@PathVariable Integer roomNumber, @Valid @RequestBody RoomDTO newRoomDTO){
        RoomDTO updatedDTO =  roomServiceInterface.updateRoomDetails(roomNumber, newRoomDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Room details updated successfully!", updatedDTO));
    }

    @DeleteMapping("/{roomNumber}")
    public ResponseEntity<ApiResponse<String>> deleteRoom(@PathVariable Integer roomNumber){
        String responseData = roomServiceInterface.deleteRoom(roomNumber);
        return ResponseEntity.ok(new ApiResponse<>(true, "Room entity deleted successfully!", responseData));
    }

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<RoomDTO>>> getAvailableRooms(@RequestParam String checkInDate, @RequestParam String checkOutDate){
        List<RoomDTO> availableRooms = roomServiceInterface.getAvailableRooms(checkInDate, checkOutDate);
        return ResponseEntity.ok(new ApiResponse<>(true, "Rooms found!", availableRooms));
    }

    @PostMapping("/book")
    public ResponseEntity<ApiResponse<String>> bookRooms(@Valid @RequestBody BookingDTO bookingDTO) throws RoomNotAvailableException {
        String responseData = roomServiceInterface.bookRooms(bookingDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Booking confirmed!", responseData));
    }

}