package com.master.bank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PaymentInformation {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentId;
    private long merchantOrderId;
    private double amount;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = true)
    private SalesAccount account;

    public PaymentInformation(SalesAccount salesAccount, double amount, long merchantOrderId) {
        this.account = salesAccount;
        this.paymentId = generatePaymentID();
        this.amount=amount;
        this.merchantOrderId = merchantOrderId;
    }


    private String generatePaymentID() {
        long randomNumberInRange = new Random().nextLong(0, 999999999);//
        long randomNumberInRange2 = new Random().nextLong(0, 10);//
        return String.valueOf(randomNumberInRange) + String.valueOf(randomNumberInRange2);
    }

}
