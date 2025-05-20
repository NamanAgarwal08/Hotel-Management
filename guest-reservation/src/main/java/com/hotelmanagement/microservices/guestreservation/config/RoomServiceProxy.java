package com.hotelmanagement.microservices.guestreservation.config;

import com.hotelmanagement.microservices.guestreservation.dto.ApiResponse;
import com.hotelmanagement.microservices.guestreservation.dto.RoomDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(value = "room")
public interface RoomServiceProxy {

    @GetMapping("/rooms/get")
    public ResponseEntity<ApiResponse<List<RoomDTO>>> getAllRooms();

}
