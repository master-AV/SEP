package com.sep.pcc.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CardDTO {
    private String PAN;
    private String securityCode;
    private String cardHolderName;
    private LocalDate expirationDate;
    private String paymentId;

}
