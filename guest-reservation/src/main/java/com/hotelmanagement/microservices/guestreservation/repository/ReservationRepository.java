package com.hotelmanagement.microservices.guestreservation.repository;

import com.hotelmanagement.microservices.guestreservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
}
