package com.sep.psp.service.interfaces;

import com.sep.psp.dto.request.PaymentRequest;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public interface IPaymentService {

    ResponseEntity<?> payWithChosenMethod(PaymentRequest paymentRequest) throws MalformedURLException, URISyntaxException;
}
