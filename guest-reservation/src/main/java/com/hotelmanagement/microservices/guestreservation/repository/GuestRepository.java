package com.hotelmanagement.microservices.guestreservation.repository;

import com.hotelmanagement.microservices.guestreservation.entity.GuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuestRepository extends JpaRepository<GuestEntity, Long> {
   Optional<GuestEntity> findByEmail(String email);
}
