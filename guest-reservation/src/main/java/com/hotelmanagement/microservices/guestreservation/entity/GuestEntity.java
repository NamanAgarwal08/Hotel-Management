package com.hotelmanagement.microservices.guestreservation.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "guests")
public class GuestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String memberCode;

    private String name;

    @Column(unique = true)
    private String email;
    private Long phone;
    private String gender;
    private String address;
    private String company;

}
