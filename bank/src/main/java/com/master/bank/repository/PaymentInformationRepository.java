package com.master.bank.repository;

import com.master.bank.model.PaymentInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInformationRepository extends JpaRepository<PaymentInformation, Long> {
    PaymentInformation findByPaymentId(String paymentId);

}
