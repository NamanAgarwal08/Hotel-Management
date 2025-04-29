package com.hotelmanagement.microservices.room.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    private Long id;

    @NotNull
    private Integer roomNumber;

    @NotNull
    private String roomType;

    @NotNull
    private Integer floorNumber;

}