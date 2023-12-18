package com.master.bank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Account {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String PAN;
    private String securityCode;
    private String cardHolderName;
    private LocalDate expirationDate;
    private int reservedMoney;
    private int availableMoney;

//    private BankType bankType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return PAN.equals(account.PAN) && securityCode.equals(account.securityCode) && cardHolderName.equals(account.cardHolderName)
                && expirationDate.getYear() == account.expirationDate.getYear() && expirationDate.getMonth() == account.expirationDate.getMonth();
    }

    @Override
    public int hashCode() {
        return Objects.hash(PAN, securityCode, cardHolderName, expirationDate);
    }
}
