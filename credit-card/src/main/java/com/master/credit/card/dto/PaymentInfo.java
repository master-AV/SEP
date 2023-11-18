package com.master.credit.card.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInfo {
    private String redirectUrl;
    private String paymentId;
}
