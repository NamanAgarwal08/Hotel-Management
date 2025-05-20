package com.hotelmanagement.microservices.room.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelmanagement.microservices.room.controller.RoomController;
import com.hotelmanagement.microservices.room.dto.RoomDTO;
import com.hotelmanagement.microservices.room.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoomService roomService;

    private RoomDTO roomDTO;

    @BeforeEach
    public void setUp(){
        this.roomDTO = new RoomDTO(1L, 101, "Normal", 1, 1000L);
    }

    @Test
    public void testCreateRoom() throws Exception {
        Mockito.when(roomService.createRoom(roomDTO)).thenReturn(roomDTO);

        mockMvc.perform(post("/rooms")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(roomDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.roomNumber").value(101))
                .andExpect(jsonPath("$.data.roomType").value("Normal"));

    }

    @Test
    public void testGetAllRooms() throws Exception {
        Mockito.when(roomService.getAllRooms()).thenReturn(List.of(roomDTO));

        mockMvc.perform(get("/rooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()").value(1))
                .andExpect(jsonPath("$.data[0].roomNumber").value(101))
                .andExpect(jsonPath("$.data[0].roomType").value("Normal"));
    }

    @Test
    public void testGetRoomByNumber() throws Exception {
        Mockito.when(roomService.getRoomByNumber(101)).thenReturn(roomDTO);

        mockMvc.perform(get("/rooms/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.roomNumber").value(101))
                .andExpect(jsonPath("$.data.roomType").value("Normal"));
    }

    @Test
    public void testUpdateRoomDetails() throws Exception {
        roomDTO.setRoomType("Deluxe");

        Mockito.when(roomService.updateRoomDetails(101, roomDTO)).thenReturn(roomDTO);

        mockMvc.perform(patch("/rooms/101")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(roomDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.roomNumber").value(101))
                .andExpect(jsonPath("$.data.roomType").value("Deluxe"));
    }

    @Test
    public void testDeleteRoom() throws Exception {
        Mockito.when(roomService.deleteRoom(101)).thenReturn("Room details of room number " + 101 + " deleted");

        mockMvc.perform(delete("/rooms/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Room details of room number " + 101 + " deleted"));
    }

    @Test
    public void testGetAvailableRooms() throws Exception {
        Mockito.when(roomService.getAvailableRooms(Mockito.any(), Mockito.any())).thenReturn(List.of(roomDTO));

        mockMvc.perform(get("/rooms/available?checkInDate=2025/05/03&checkOutDate=2025/05/04"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()").value(1))
                .andExpect(jsonPath("$.data[0].roomNumber").value(101))
                .andExpect(jsonPath("$.data[0].roomType").value("Normal"));
    }

}