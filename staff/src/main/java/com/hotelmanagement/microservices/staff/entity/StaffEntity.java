package com.hotelmanagement.microservices.staff.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "staffs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String staffCode;

    private String employeeName;

    @Column(unique = true)
    private String email;
    private Integer age;

    @Column(unique = true)
    private String nic;

    private Long phoneNumber;
    private String employeeAddress;
    private Long salary;
    private String occupation;

}