package com.sep.psp.service.interfaces;

import ftn.sep.dto.request.PaymentRequest;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public interface IPaymentService {

    ResponseEntity<?> payWithChosenMethod(PaymentRequest paymentRequest) throws MalformedURLException, URISyntaxException;
}
