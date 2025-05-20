package com.hotelmanagement.microservices.guestreservation.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelmanagement.microservices.guestreservation.controller.ReservationController;
import com.hotelmanagement.microservices.guestreservation.dto.ReservationDTO;
import com.hotelmanagement.microservices.guestreservation.service.ReservationService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReservationService reservationService;

    private ReservationDTO reservationDTO;

    @BeforeEach
    public void setUp() {
        this.reservationDTO = new ReservationDTO(1L, 1L, List.of(101), "2025/05/03", "2025/05/04", 2, 0);
    }

//    @Test
//    public void testCreateReservation() throws Exception {
//        Mockito.when(reservationService.createReservation(reservationDTO)).thenReturn(reservationDTO);
//
//        mockMvc.perform(post("/reservations")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(reservationDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.id").value(1L))
//                .andExpect(jsonPath("$.data.checkInDate").value("2025/05/03"))
//                .andExpect(jsonPath("$.data.checkOutDate").value("2025/05/04"));
//
//    }

    @Test
    public void testGetAllReservations() throws Exception {
        Mockito.when(reservationService.getAllReservations()).thenReturn(List.of(reservationDTO));

        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()").value(1))
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].checkInDate").value("2025/05/03"))
                .andExpect(jsonPath("$.data[0].checkOutDate").value("2025/05/04"));
    }

    @Test
    public void testGetReservation() throws Exception {
        Mockito.when(reservationService.getReservations(1L)).thenReturn(reservationDTO);

        mockMvc.perform(get("/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.checkInDate").value("2025/05/03"))
                .andExpect(jsonPath("$.data.checkOutDate").value("2025/05/04"));
    }

    @Test
    public void testUpdateReservationsDetails() throws Exception {
        reservationDTO.setNumberOfChildren(2);

        Mockito.when(reservationService.updateReservationDetails(1L, reservationDTO)).thenReturn(reservationDTO);

        mockMvc.perform(patch("/reservations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(reservationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.checkInDate").value("2025/05/03"))
                .andExpect(jsonPath("$.data.checkOutDate").value("2025/05/04"))
                .andExpect(jsonPath("$.data.numberOfChildren").value(2));
    }

    @Test
    public void testDeleteReservation () throws Exception {
        Mockito.when(reservationService.deleteReservation(1L)).thenReturn("Reservation details deleted successfully!");

        mockMvc.perform(delete("/reservations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Reservation details deleted successfully!"));
    }

//    @Test
//    public void testBookRooms() throws Exception{
//
//        BookingDTO bookingDTO = new BookingDTO(List.of(101), "2025/05/03","2025/05/04" );
//
//        Mockito.when(reservationService.bookRooms(bookingDTO)).thenReturn(List.of(1L,2L));
//
//        mockMvc.perform(post("/reservations/book")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(bookingDTO)))
//                        .andExpect(status().isOk())
//                        .andExpect(jsonPath("$.data[0]").value(1L));
//    }
}