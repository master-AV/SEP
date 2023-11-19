package com.master.credit.card.controller;

import com.master.credit.card.dto.CardDTO;
import com.master.credit.card.dto.PaymentInfo;
import com.master.credit.card.dto.PaymentUrlDTO;
import com.master.credit.card.service.CCPaymentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/cc")
public class CCPaymentController {

    @Autowired
    private CCPaymentService paymentService;

//    @GetMapping(value = "/pay")
    @PostMapping(value = "/req/payment", consumes = "application/json")
    @CrossOrigin(exposedHeaders = {"Location"})
//    @ResponseStatus(HttpStatus.TEMPORARY_REDIRECT)
    public ResponseEntity<?> requestPaymentURL(@RequestBody PaymentUrlDTO dto,
                                               HttpServletResponse response) throws URISyntaxException {

//        PaymentInfo info = paymentService.requestPaymentURL(dto);
//        HttpHeaders headers = new HttpHeaders();
//        response.addHeader("Location", info.getRedirectUrl());
////        response.setStatus(HttpServletResponse.SC_FOUND);
////        headers.add
//        return info.getPaymentId();//, HttpStatus.FOUND);
        PaymentInfo info = paymentService.requestPaymentURL(dto);
        HttpHeaders headers = new HttpHeaders();
        System.out.println(info.getRedirectUrl());
        if (info.getPaymentId() != null) info.setRedirectUrl(info.getRedirectUrl()+ "/" + info.getPaymentId());
        headers.add("Location", info.getRedirectUrl());
        return new ResponseEntity(info.getPaymentId(), headers, HttpStatus.OK);
//        return ResponseEntity.status(301).location(new URI()).build();
    }

    @PostMapping(value = "/pay", consumes = "application/json")
    @CrossOrigin(exposedHeaders = {"Location"})
    public ResponseEntity<?> startPaymentWithCC(@RequestBody CardDTO cardInfo){
        System.out.println("PAN: " + cardInfo.getPan());
        System.out.println("sec: " + cardInfo.getSecurityCode());
        System.out.println("name: " + cardInfo.getCardHolderName());
        System.out.println("date: " + cardInfo.getExpirationDate());
        System.out.println("pay id: " + cardInfo.getPaymentId());
        ResponseEntity<?> res =  paymentService.startPaymentWithCC(cardInfo);
        return res;
//        return null;
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Location", info.getRedirectUrl());
//        return new ResponseEntity(info.getPaymentId(), headers, HttpStatus.FOUND);
    }
}
