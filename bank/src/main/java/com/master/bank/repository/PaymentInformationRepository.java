package com.master.bank.repository;

import com.master.bank.model.PaymentInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentInformationRepository extends JpaRepository<PaymentInformation, Long> {
    Optional<PaymentInformation> findByPaymentId(String paymentId);

}
