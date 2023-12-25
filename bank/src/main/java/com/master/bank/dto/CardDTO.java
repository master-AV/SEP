package com.master.bank.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CardDTO {
    protected String PAN;
    protected String securityCode;
    protected String cardHolderName;
    protected LocalDate expirationDate;
    protected String paymentId;

}
