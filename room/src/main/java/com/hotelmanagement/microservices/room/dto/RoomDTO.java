package com.hotelmanagement.microservices.room.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private Long id;

    @NotNull(message = "Please enter room number!")
    private Integer roomNumber;

    @NotBlank(message = "Please specify room type!")
    private String roomType;

    @NotNull(message = "Floor number field is empty!")
    private Integer floorNumber;

    @NotNull(message = "Please enter room amount!")
    private Long amount;
}