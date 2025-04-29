package com.hotelmanagement.microservices.guestreservation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "entities")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private Long guestId;

    @ElementCollection
    @CollectionTable(name = "reservation_rooms", joinColumns = @JoinColumn(name = "reservation_id"))
    @Column(name = "room_number")
    private List<Integer> roomNumbers;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkInDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate checkOutDate;

    private int numberOfAdults;
    private int numberOfChildren;

}