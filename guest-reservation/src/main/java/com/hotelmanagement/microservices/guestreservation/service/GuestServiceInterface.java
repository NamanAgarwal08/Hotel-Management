package com.hotelmanagement.microservices.guestreservation.service;

import com.hotelmanagement.microservices.guestreservation.dto.GuestDTO;

import java.util.List;

public interface GuestServiceInterface {

    GuestDTO addGuest(GuestDTO guestDto);

    List<GuestDTO> getAllGuests();

    GuestDTO getGuest(String email);

    GuestDTO updateGuest(Long id, GuestDTO newDto);

    String deleteGuest(Long id);
}
