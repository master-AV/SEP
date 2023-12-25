package com.sep.psp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
    protected String pan;
    protected String securityCode;
    protected String cardHolderName;
    protected LocalDate expirationDate;
    protected String paymentId;
}
