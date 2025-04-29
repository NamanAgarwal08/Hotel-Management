package com.hotelmanagement.microservices.guestreservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {

    @NotNull
    private List<Integer> roomNumbers;

    @NotNull
    private String checkInDate;

    @NotNull
    private String checkOutDate;

}