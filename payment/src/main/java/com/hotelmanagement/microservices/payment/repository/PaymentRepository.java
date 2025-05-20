package com.hotelmanagement.microservices.payment.repository;

import com.hotelmanagement.microservices.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity,Long> {
    PaymentEntity findBySessionId(String sessionId);
}
