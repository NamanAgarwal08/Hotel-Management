package com.hotelmanagement.microservices.guestreservation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuestDTO {

    private Long id;
    private String memberCode;

    @NotNull(message = "Guest name required!")
    private String name;

    @NotNull(message = "Email can not be null!")
    private String email;

    @NotNull(message = "Phone number required!")
    private Long phone;

    private String gender;
    private String address;
    private String company;


}
