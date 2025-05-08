package com.hotelmanagement.microservices.staff.repository;

import com.hotelmanagement.microservices.staff.entity.StaffEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<StaffEntity, Long> {

    Optional<StaffEntity> findByStaffCode(String staffCode);

}