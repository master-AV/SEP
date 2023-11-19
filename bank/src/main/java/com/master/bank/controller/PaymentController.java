package com.master.bank.controller;

import com.master.bank.dto.*;
import com.master.bank.service.PaymentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bank")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private Environment environment;

    @PostMapping(value = "/request")
    public ResponseEntity<?> requestPaymentCC(@RequestBody PaymentURLRequestDTO requestDTO,
                                       HttpServletResponse response){
        try {
            System.out.println("Bank - controller - arrived");
            PaymentInfoDTO info = paymentService.requestPayment(requestDTO);
            response.addHeader("PaymentUrl", info.getPaymentUrl());
            response.addHeader("PaymentId", info.getPaymentId());
            return ResponseEntity.ok("");
        }catch (Exception ex){
            response.addHeader("ErrorUrl", environment.getProperty("bank.url.error"));
            return ResponseEntity.ok("");
        }
    }
    @PostMapping(value = "/start/payment")
    @CrossOrigin(exposedHeaders = {"Location"})
    public ResponseEntity<?> payWithCC(@RequestBody CardDTO cardDTO){
        HttpHeaders headers = new HttpHeaders();
        try {
            System.out.println("START PAYMENT");
            EndPaymentDTO endPaymentDTO = paymentService.startPayment(cardDTO, false);
            switch (endPaymentDTO.getTransactionState()) {
                case FAILED -> headers.add("Location", environment.getProperty("bank.url.failed"));
                case SUCCESSFUL -> headers.add("Location", environment.getProperty("bank.url.success.pay"));
                default -> headers.add("Location", environment.getProperty("bank.url.error"));
            }
        }catch (Exception e){
            headers.add("Location", environment.getProperty("bank.url.error"));
        }
        return new ResponseEntity<>("", headers, HttpStatus.OK);
    }

    @PostMapping(value = "/pcc/req")
    public ResponseEntity<?> payWithCCFromPCC(@RequestBody PccRequestDTO pccRequestDTO){
        System.out.println("START PAYMENT");
        paymentService.startPaymentFromPCC(pccRequestDTO);
        return null;
    }

    @PostMapping(value = "/pcc/res")
    public ResponseEntity<?> payWithCCFromPCCRes(@RequestBody PccResponseDTO pccResponseDTO){
        paymentService.endPaymentFromPCC(pccResponseDTO);
        return null;
    }

}
