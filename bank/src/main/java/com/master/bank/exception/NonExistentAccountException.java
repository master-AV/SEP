package com.master.bank.exception;

public class NonExistentAccountException extends RuntimeException {

    private String failedPaymentUrl;

    public NonExistentAccountException() {}

    public NonExistentAccountException(String message) {
        super(message);
    }

}
