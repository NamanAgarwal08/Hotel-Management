package com.hotelmanagement.microservices.staff.controller;


import com.hotelmanagement.microservices.staff.dto.ApiResponse;
import com.hotelmanagement.microservices.staff.dto.StaffDTO;
import com.hotelmanagement.microservices.staff.service.StaffServiceInterface;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffServiceInterface staffServiceInterface;

    @PostMapping
    public ResponseEntity<ApiResponse<StaffDTO>> createStaff(@Valid @RequestBody StaffDTO staffDTO){
        StaffDTO responseDTO = staffServiceInterface.createStaff(staffDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Staff entity created successfully!", responseDTO));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StaffDTO>>> getStaffDetails(){
        List<StaffDTO> responseList =  staffServiceInterface.getStaffDetails();
        return ResponseEntity.ok(new ApiResponse<>(true, "List fetched from database!", responseList));
    }

    @GetMapping("/{staffCode}")
    public ResponseEntity<ApiResponse<StaffDTO>> getStaffByCode(@PathVariable String staffCode){
        StaffDTO responseDTO = staffServiceInterface.getStaffByCode(staffCode);
        return ResponseEntity.ok(new ApiResponse<>(true, "Staff details found!", responseDTO));
    }

    @PatchMapping("/{staffCode}")
    public ResponseEntity<ApiResponse<StaffDTO>> updateStaffDetails(@PathVariable String staffCode, @Valid @RequestBody StaffDTO staffDTO){
        StaffDTO updatedDTO = staffServiceInterface.updateStaffDetails(staffCode, staffDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "Staff details updated successfully!", updatedDTO));
    }

    @DeleteMapping("/{staffCode}")
    public ResponseEntity<ApiResponse<String>> deleteStaffDetails(@PathVariable String staffCode){
        String responseData =  staffServiceInterface.deleteStaffDetails(staffCode);
        return ResponseEntity.ok(new ApiResponse<>(true, "Staff entity deleted!", responseData));
    }

}
