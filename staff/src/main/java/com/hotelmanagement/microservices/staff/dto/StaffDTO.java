package com.hotelmanagement.microservices.staff.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffDTO {

    private Long id;

    private String staffCode;

    @NotNull(message = "Name field can't be null!")
    private String employeeName;

    @Email(message = "Enter email in correct format!")
    @NotNull(message = "Email field can't be null!")
    private String email;

    private Integer age;
    private String nic;

    @NotNull(message = "Phone Number is required!")
    private Long phoneNumber;

    @NotNull
    private String employeeAddress;

    private Long salary;
    private String occupation;

}