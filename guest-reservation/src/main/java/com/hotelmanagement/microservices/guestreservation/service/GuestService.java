package com.hotelmanagement.microservices.guestreservation.service;

import com.hotelmanagement.microservices.guestreservation.dto.GuestDTO;
import com.hotelmanagement.microservices.guestreservation.entity.GuestEntity;
import com.hotelmanagement.microservices.guestreservation.exception.ResourceNotFoundException;
import com.hotelmanagement.microservices.guestreservation.repository.GuestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService implements GuestServiceInterface{

    @Autowired
    GuestRepository guestRepository;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public GuestDTO addGuest(GuestDTO guestDto) {
        GuestEntity guestEntity = modelMapper.map(guestDto, GuestEntity.class);
        GuestEntity savedEntity = guestRepository.save(guestEntity);
        return modelMapper.map(savedEntity, GuestDTO.class);
    }


    @Override
    public List<GuestDTO> getAllGuests() {
        return guestRepository.findAll().stream().map(guest -> modelMapper.map(guest, GuestDTO.class)).toList();
    }

    @Override
    public GuestDTO getGuest(String email) {
        GuestEntity foundEntity = guestRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("No guest details found with email: " + email));

        return modelMapper.map(foundEntity, GuestDTO.class);
    }

    @Override
    public GuestDTO updateGuest(Long id, GuestDTO newDto) {
        GuestEntity foundEntity = guestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No guest details found with id: " + id));

        modelMapper.map(newDto, foundEntity);
        GuestEntity updatedEntity = guestRepository.save(foundEntity);

        return modelMapper.map(updatedEntity, GuestDTO.class);
    }

    @Override
    public String deleteGuest(Long id) {
        GuestEntity foundEntity = guestRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No guest details found with id: " + id));

        guestRepository.delete(foundEntity);

        return "Guest details deleted successfully!";
    }

}