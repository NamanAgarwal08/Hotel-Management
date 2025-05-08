package com.hotelmanagement.microservices.staff.service;

import com.hotelmanagement.microservices.staff.dto.StaffDTO;

import java.util.List;

public interface StaffServiceInterface {
    StaffDTO createStaff(StaffDTO staffDTO);

    List<StaffDTO> getStaffDetails();

    StaffDTO getStaffByCode(String staffCode);

    StaffDTO updateStaffDetails(String staffCode, StaffDTO staffDTO);

    String deleteStaffDetails(String staffCode);

}
