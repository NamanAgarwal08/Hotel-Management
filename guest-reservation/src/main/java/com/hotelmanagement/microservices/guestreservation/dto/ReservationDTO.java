package com.hotelmanagement.microservices.guestreservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {

    private Long Id;
    private Long guestId;
    private List<Integer> roomNumbers;
    private String checkInDate;
    private String checkOutDate;
    private int numberOfAdults;
    private int numberOfChildren;

}