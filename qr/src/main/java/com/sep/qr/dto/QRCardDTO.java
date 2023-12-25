package com.sep.qr.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QRCardDTO {
    private String pan;
    private String securityCode;
    private String cardHolderName;
    private LocalDate expirationDate;
    private String paymentId;

    private String merchantInformation;

}
