package com.hotelmanagement.microservices.staff.servicetest;

import com.hotelmanagement.microservices.staff.dto.StaffDTO;
import com.hotelmanagement.microservices.staff.entity.StaffEntity;
import com.hotelmanagement.microservices.staff.repository.StaffRepository;
import com.hotelmanagement.microservices.staff.service.StaffService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StaffServiceTest {

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StaffService staffService;

    private StaffEntity staffEntity;
    private StaffDTO staffDTO;

    @BeforeEach
    public void setUp(){
        this.staffEntity = new StaffEntity(1L, "HM9999999999", "ABCD", "abcd@gmail.com", 23, "NIC", 9999999999L, "my address", 100000L, "none");
        this.staffDTO = new StaffDTO(1L, "HM9999999999", "ABCD", "abcd@gmail.com", 23, "NIC", 9999999999L, "my address", 100000L, "none");

        Mockito.lenient().when(staffRepository.save(staffEntity)).thenReturn(staffEntity);
        Mockito.lenient().when(staffRepository.findByStaffCode("HM9999999999")).thenReturn(Optional.of(staffEntity));
        Mockito.lenient().when(modelMapper.map(staffEntity, StaffDTO.class)).thenReturn(staffDTO);
        Mockito.lenient().when(modelMapper.map(staffDTO, StaffEntity.class)).thenReturn(staffEntity);
    }


    @Test
    void testCreateStaff(){

        StaffDTO savedDto = staffService.createStaff(staffDTO);
        Assertions.assertNotNull(savedDto);

        Assertions.assertEquals(1L,savedDto.getId());
        Assertions.assertEquals("ABCD", savedDto.getEmployeeName());
        Assertions.assertEquals("HM9999999999", savedDto.getStaffCode());

    }

    @Test
    void tesGetStaffDetails(){
        Mockito.when(staffRepository.findAll()).thenReturn(List.of(staffEntity));
        List<StaffDTO> lst = staffService.getStaffDetails();

        Assertions.assertEquals(1, lst.size());
        Assertions.assertEquals(staffDTO, lst.get(0));

    }

    @Test
    void testGetStaffByCode(){
        Mockito.when(staffRepository.findByStaffCode("HM9999999999")).thenReturn(Optional.of(staffEntity));

        StaffDTO  foundDto = staffService.getStaffByCode("HM9999999999");

        Assertions.assertNotNull(foundDto);

        Assertions.assertEquals(1L,foundDto.getId());
        Assertions.assertEquals("ABCD", foundDto.getEmployeeName());
        Assertions.assertEquals("HM9999999999", foundDto.getStaffCode());

    }

    @Test
    void testUpdateStaffDetails(){
        StaffDTO updatedDto = new StaffDTO(1L, "HM9999999999", "EFGH", "abcd@gmail.com", 23, "NIC", 9999999999L, "my address", 100000L, "none");
        StaffEntity updatedEntity = new StaffEntity(1L, "HM9999999999", "EFGH", "abcd@gmail.com", 23, "NIC", 9999999999L, "my address", 100000L, "none");

        Mockito.doNothing().when(modelMapper).map(staffDTO, staffEntity);

        Mockito.when(staffRepository.save(staffEntity)).thenReturn(updatedEntity);
        Mockito.when(modelMapper.map(updatedEntity, StaffDTO.class)).thenReturn(updatedDto);

        StaffDTO resultDto = staffService.updateStaffDetails("HM9999999999",staffDTO);

        Assertions.assertNotNull(resultDto);
        Assertions.assertEquals("EFGH", resultDto.getEmployeeName());

    }

    @Test
    void testDeleteStaffDetails(){
        String result = staffService.deleteStaffDetails("HM9999999999");

    }

}