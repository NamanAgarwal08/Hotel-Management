package com.hotelmanagement.microservices.room.repository;

import com.hotelmanagement.microservices.room.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    Optional<RoomEntity> findByRoomNumber(Integer roomNumber);

    @Query("SELECT r FROM RoomEntity r WHERE r.roomNumber NOT IN (" +
            "SELECT b.roomNumber FROM BookingEntity b " +
            "WHERE b.checkInDate < :checkOutDate AND b.checkOutDate > :checkInDate" + ")")
    List<RoomEntity> findAvailableRoomsBetweenDates(String checkInDate, String checkOutDate);

}