package com.sep.psp.dto;

import ftn.sep.db.AccountInformation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QRCardDTO extends CardDTO{

    private String merchantInformation;
    public QRCardDTO(AccountInformation acc, String paymentId, String merchantInformation) {
        this.pan = acc.getPAN();
        this.securityCode = acc.getSecurityCode();
        this.cardHolderName = acc.getCardHolderName();
        this.expirationDate = LocalDate.parse(acc.getExpirationDate());
        this.paymentId = paymentId;
        this.merchantInformation = merchantInformation;
    }
}
