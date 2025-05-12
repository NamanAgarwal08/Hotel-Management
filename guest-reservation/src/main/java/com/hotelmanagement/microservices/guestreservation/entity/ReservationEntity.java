package com.hotelmanagement.microservices.guestreservation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reservations", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"roomNumbers", "checkInDate", "checkOutDate"})
})
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private Long guestId;

    private List<Integer> roomNumbers;

    private String checkInDate;

    private String checkOutDate;

    private Integer numberOfAdults;
    private Integer numberOfChildren;

    private Boolean status;

}