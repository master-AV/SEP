package com.master.bank.exception;

public class NotValidQRCodeException extends RuntimeException {

    public NotValidQRCodeException() {}

    public NotValidQRCodeException(String message) {
        super(message);
    }
}