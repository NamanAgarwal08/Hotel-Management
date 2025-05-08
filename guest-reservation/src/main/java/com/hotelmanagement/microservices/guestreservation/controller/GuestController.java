package com.hotelmanagement.microservices.guestreservation.controller;

import com.hotelmanagement.microservices.guestreservation.dto.ApiResponse;
import com.hotelmanagement.microservices.guestreservation.dto.GuestDTO;
import com.hotelmanagement.microservices.guestreservation.service.GuestServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/guests")
public class GuestController {

    @Autowired
    GuestServiceInterface guestServiceInterface;


    @PostMapping
    public ResponseEntity<ApiResponse<GuestDTO>> addGuest(@Valid @RequestBody GuestDTO guestDto){
        GuestDTO responseDTO = guestServiceInterface.addGuest(guestDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Guest entity added successfully!", responseDTO));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GuestDTO>>> getAllGuests(){
       List<GuestDTO> guestList = guestServiceInterface.getAllGuests();
       return ResponseEntity.ok(new ApiResponse<>(true, "List fetched from database!", guestList));
    }

    @GetMapping("/{email}")
    public ResponseEntity<ApiResponse<GuestDTO>> getGuest(@PathVariable String email){
       GuestDTO responseDTO = guestServiceInterface.getGuest(email);
       return ResponseEntity.ok(new ApiResponse<>(true, "Guest entity found!", responseDTO));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<GuestDTO>> updateGuest(@PathVariable Long id, @Valid @RequestBody GuestDTO newDto){
        GuestDTO updatedDTO = guestServiceInterface.updateGuest(id, newDto);
        return ResponseEntity.ok(new ApiResponse<>(true, "Guest details updated successfully!", updatedDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteGuest(@PathVariable Long id){
        String responseData =  guestServiceInterface.deleteGuest(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Guest entity deleted successfully!", responseData));
    }
}
