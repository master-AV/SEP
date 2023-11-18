package com.master.bank.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotValidPaymentRequestException  extends RuntimeException {

    private String failedPaymentUrl;

    public NotValidPaymentRequestException() {}

    public NotValidPaymentRequestException(String message) {
        super(message);
    }
    public NotValidPaymentRequestException(String message, String url) {
        super(message);
        this.failedPaymentUrl = url;
    }

}
