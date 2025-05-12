package com.hotelmanagement.microservices.guestreservation.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelmanagement.microservices.guestreservation.controller.GuestController;
import com.hotelmanagement.microservices.guestreservation.dto.GuestDTO;
import com.hotelmanagement.microservices.guestreservation.service.GuestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GuestController.class)
public class GuestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GuestService guestService;

    private GuestDTO guestDTO;

    @BeforeEach
    public void setUp() {
        this.guestDTO = new GuestDTO(1L, null, "Guest", "guest@gmail.com", 9999999999L, null, null, null);
    }

    @Test
    public void testAddGuest() throws Exception {
        Mockito.when(guestService.addGuest(guestDTO)).thenReturn(guestDTO);

        mockMvc.perform(post("/guests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(guestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Guest"));

    }

    @Test
    public void testGetAllGuests() throws Exception {
        Mockito.when(guestService.getAllGuests()).thenReturn(List.of(guestDTO));

        mockMvc.perform(get("/guests"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()").value(1))
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].name").value("Guest"));
    }

//    @Test
//    public void testGetGuest() throws Exception {
//        Mockito.when(guestService.getGuest(1L)).thenReturn(guestDTO);
//
//        mockMvc.perform(get("/guests/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.id").value(1L))
//                .andExpect(jsonPath("$.data.name").value("Guest"));
//    }

    @Test
    public void testUpdateGuest() throws Exception {
        guestDTO.setEmail("guest1@gmail.com");

        Mockito.when(guestService.updateGuest(1L, guestDTO)).thenReturn(guestDTO);

        mockMvc.perform(patch("/guests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(guestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.name").value("Guest"))
                .andExpect(jsonPath("$.data.email").value("guest1@gmail.com"));
    }

        @Test
        public void testDeleteGuest () throws Exception {
            Mockito.when(guestService.deleteGuest(1L)).thenReturn("Guest details deleted successfully!");

            mockMvc.perform(delete("/guests/1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data").value("Guest details deleted successfully!"));
        }

}