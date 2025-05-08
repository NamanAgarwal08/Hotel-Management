package com.hotelmanagement.microservices.staff.service;

import com.hotelmanagement.microservices.staff.dto.StaffDTO;
import com.hotelmanagement.microservices.staff.entity.StaffEntity;
import com.hotelmanagement.microservices.staff.exception.ResourceNotFoundException;
import com.hotelmanagement.microservices.staff.repository.StaffRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StaffService implements StaffServiceInterface{

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public StaffDTO createStaff(StaffDTO staffDTO) {
        StaffEntity staffEntity = modelMapper.map(staffDTO, StaffEntity.class);

        //staffCode
        String code = "HM"+staffDTO.getPhoneNumber();
        staffEntity.setStaffCode(code);

        StaffEntity savedEntity = staffRepository.save(staffEntity);

        return modelMapper.map(savedEntity, StaffDTO.class);
    }

    @Override
    public List<StaffDTO> getStaffDetails() {
        return staffRepository.findAll().stream().map(staff -> modelMapper.map(staff, StaffDTO.class)).toList();
    }

    @Override
    public StaffDTO getStaffByCode(String staffCode) {
        StaffEntity foundEntity = staffRepository.findByStaffCode(staffCode).orElseThrow(() -> new ResourceNotFoundException("No staff details found with the provided staff code: " + staffCode));
        return modelMapper.map(foundEntity, StaffDTO.class);
    }

    @Override
    public StaffDTO updateStaffDetails(String staffCode, StaffDTO staffDTO) {
        StaffEntity foundEntity = staffRepository.findByStaffCode(staffCode).orElseThrow(() -> new ResourceNotFoundException("No staff details found with the provided staff code: " + staffCode));
        modelMapper.map(staffDTO, foundEntity);

        StaffEntity savedEntity = staffRepository.save(foundEntity);

        return modelMapper.map(savedEntity, StaffDTO.class);
    }

    @Override
    public String deleteStaffDetails(String staffCode) {
        StaffEntity foundEntity = staffRepository.findByStaffCode(staffCode).orElseThrow(() -> new ResourceNotFoundException("No staff details found with the provided staff code: " + staffCode));
        staffRepository.delete(foundEntity);
        return "Staff Details Deleted Successfully";
    }


}