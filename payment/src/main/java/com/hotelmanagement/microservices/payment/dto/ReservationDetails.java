package com.hotelmanagement.microservices.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDetails {

    private Long amount;
    private Long quantity;
    private String name;
    private String currency;

}
