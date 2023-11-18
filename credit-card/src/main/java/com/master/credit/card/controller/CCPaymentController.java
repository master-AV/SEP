package com.master.credit.card.controller;

import com.master.credit.card.dto.CardDTO;
import com.master.credit.card.dto.PaymentInfo;
import com.master.credit.card.dto.PaymentUrlDTO;
import com.master.credit.card.service.CCPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cc")
public class CCPaymentController {

    @Autowired
    private CCPaymentService paymentService;

//    @GetMapping(value = "/pay")
    @PostMapping(value = "/req", consumes = "application/json")
    public ResponseEntity<?> requestPaymentURL(@RequestBody PaymentUrlDTO dto){

        PaymentInfo info = paymentService.requestPaymentURL(dto);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", info.getRedirectUrl());
        return new ResponseEntity(info.getPaymentId(), headers, HttpStatus.FOUND);
    }

    @PostMapping(value = "/pay", consumes = "application/json")
    public ResponseEntity<?> startPaymentWithCC(@RequestBody CardDTO cardInfo){
        System.out.println("PAN: " + cardInfo.getPan());
        System.out.println("sec: " + cardInfo.getSecurityCode());
        System.out.println("name: " + cardInfo.getCardHolderName());
        System.out.println("date: " + cardInfo.getExpirationDate());
        System.out.println("pay id: " + cardInfo.getPaymentId());
        paymentService.startPaymentWithCC(cardInfo);
        return null;
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Location", info.getRedirectUrl());
//        return new ResponseEntity(info.getPaymentId(), headers, HttpStatus.FOUND);
    }
}
