package com.bripay.paymentservice.repository;

import com.bripay.commonsservice.dto.PaymentDto;
import com.bripay.commonsservice.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
    boolean existsBySenderAccount(String senderAccount);
    boolean existsByBeneficiaryAccount(String beneficiaryAccount);
    List<PaymentEntity> findAllBySenderAccount(String senderAccount);
    List<PaymentEntity> findByPaymentDateBetween(Date fromDate, Date toDate);
}
