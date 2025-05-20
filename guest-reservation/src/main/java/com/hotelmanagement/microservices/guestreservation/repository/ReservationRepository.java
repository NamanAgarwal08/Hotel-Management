package com.hotelmanagement.microservices.guestreservation.repository;

import com.hotelmanagement.microservices.guestreservation.dto.RoomDTO;
import com.hotelmanagement.microservices.guestreservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

    @Query("SELECT r FROM ReservationEntity r  WHERE (r.checkOutDate >= :checkInDate AND checkInDate <= :checkOutDate) AND status != 'CANCELLED'")
    List<ReservationEntity> checkAvailability(String checkInDate, String checkOutDate);

    Optional<ReservationEntity> findBySessionId(String sessionId);
}
