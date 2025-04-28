package com.hotelmanagement.microservices.guestreservation.repository;

import com.hotelmanagement.microservices.guestreservation.entity.GuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<GuestEntity, Long> {
}
