package com.hotelmanagement.microservices.room.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "bookings",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"roomNumber", "checkInDate", "checkOutDate"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String checkInDate;
    private String checkOutDate;

    private Integer roomNumber;

    private String status ;

}