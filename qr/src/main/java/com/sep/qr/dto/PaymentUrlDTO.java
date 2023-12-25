package com.sep.qr.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentUrlDTO {
    private int amount;
    private String merchantId;
    private String merchantPassword;
}
