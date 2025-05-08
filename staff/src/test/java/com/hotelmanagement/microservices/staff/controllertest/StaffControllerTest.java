package com.hotelmanagement.microservices.staff.controllertest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelmanagement.microservices.staff.controller.StaffController;
import com.hotelmanagement.microservices.staff.dto.StaffDTO;
import com.hotelmanagement.microservices.staff.service.StaffService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StaffController.class)
public class StaffControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StaffService staffService;

    private StaffDTO staffDTO;

    @BeforeEach
    void setUp(){
        this.staffDTO = new StaffDTO(1L, "HM9999999999", "ABCD", "abcd@gmail.com", 23, "NIC", 9999999999L, "my address", 100000L, "none");
    }


    @Test
    public void testCreateStaff() throws Exception {
        Mockito.when(staffService.createStaff(staffDTO)).thenReturn(staffDTO);

        mockMvc.perform(post("/staff")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(staffDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.staffCode").value("HM9999999999"));

    }


    @Test
    public void testGetStaffDetails() throws Exception {
        Mockito.when(staffService.getStaffDetails()).thenReturn(List.of(staffDTO));

        mockMvc.perform(get("/staff"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.size()").value(1))
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].staffCode").value("HM9999999999"));

    }


    @Test
    public void testGetStaffByCode() throws Exception {
        Mockito.when(staffService.getStaffByCode("HM9999999999")).thenReturn(staffDTO);

        mockMvc.perform(get("/staff/HM9999999999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.staffCode").value("HM9999999999"));
    }

    
    @Test
    public void testUpdateStaffDetails() throws Exception {

        staffDTO.setStaffCode("HM8888888888");

        Mockito.when(staffService.updateStaffDetails(Mockito.eq("HM9999999999"), Mockito.any())).thenReturn(staffDTO);

        mockMvc.perform(patch("/staff/HM9999999999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(staffDTO)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.data.id").value(1L))
                        .andExpect(jsonPath("$.data.staffCode").value("HM8888888888"));

    }


    @Test
    public void testDeleteStaffDetails() throws Exception {

        Mockito.when(staffService.deleteStaffDetails(Mockito.eq("HM9999999999"))).thenReturn("Staff Details Deleted Successfully");

        mockMvc.perform(delete("/staff/HM9999999999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("Staff Details Deleted Successfully"));


    }

}