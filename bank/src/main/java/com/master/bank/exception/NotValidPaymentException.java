package com.master.bank.exception;

public class NotValidPaymentException extends RuntimeException {

    private String failedPaymentUrl;

    public NotValidPaymentException() {}

    public NotValidPaymentException(String message) {
        super(message);
    }
}
