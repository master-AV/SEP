package com.master.bank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QRMerchantDTO {
    private String recipientPAN;
    private String recipientName;
    private String currency = "RSD";
    private double amount;

    public QRMerchantDTO(String PAN, String name, double amount){
        this.recipientPAN = PAN;
        this.recipientName = name;
        this.amount = amount;
    }

}
