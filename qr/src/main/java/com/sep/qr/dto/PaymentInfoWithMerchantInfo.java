package com.sep.qr.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInfoWithMerchantInfo {
    private String redirectUrl;
    private String paymentId;
    private String qrCode;

    public PaymentInfoWithMerchantInfo(String qrCode){
        this.qrCode = qrCode;
    }
}
