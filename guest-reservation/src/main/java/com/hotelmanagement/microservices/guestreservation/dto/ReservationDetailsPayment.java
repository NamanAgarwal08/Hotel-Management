package com.hotelmanagement.microservices.guestreservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDetailsPayment {

    private Long amount;
    private String currency;
    private List<Integer> rooms;
    private String checkInDate;
    private String checkOutDate;

}
