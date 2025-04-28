package com.hotelmanagement.microservices.guestreservation.controller;

import com.hotelmanagement.microservices.guestreservation.dto.GuestDTO;
import com.hotelmanagement.microservices.guestreservation.service.GuestServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/guests")
public class GuestController {

    @Autowired
    GuestServiceInterface guestServiceInterface;


    @PostMapping
    public GuestDTO addGuest(@Valid @RequestBody GuestDTO guestDto){
        return guestServiceInterface.addGuest(guestDto);
    }

    @GetMapping
    public List<GuestDTO> getAllGuests(){
        return guestServiceInterface.getAllGuests();
    }

    @GetMapping("/{id}")
    public GuestDTO getGuest(@PathVariable Long id){
        return guestServiceInterface.getGuest(id);
    }

    @PatchMapping("/{id}")
    public GuestDTO updateGuest(@PathVariable Long id, @RequestBody GuestDTO newDto){
        return guestServiceInterface.updateGuest(id, newDto);
    }

    @DeleteMapping("/{id}")
    public String deleteGuest(@PathVariable Long id){
        return guestServiceInterface.deleteGuest(id);
    }
}
