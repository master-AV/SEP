package com.sep.psp.controller;

import com.sep.psp.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cc")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping(value = "/pay/{amount}")
    public ResponseEntity<?> startRequestPaymentCC(@PathVariable int amount){
        System.out.println("Pay");
        return paymentService.startPayment(amount);
    }

}
