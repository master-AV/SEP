package com.master.bank.dto;

import com.master.bank.model.PaymentInformation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInfoDTO {
    private String paymentUrl;
    private String paymentId;

    private long merchantOrderId;

    public PaymentInfoDTO(String paymentURL, PaymentInformation paymentInformation, long merchantOrderId) {
        this.paymentId = paymentInformation.getPaymentId();
        this.paymentUrl = paymentURL;
        this.merchantOrderId = merchantOrderId;
    }
}
