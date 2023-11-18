package com.master.bank.controller;

import com.master.bank.dto.*;
import com.master.bank.service.PaymentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
    public ResponseEntity<?> payWithCC(@RequestBody CardDTO cardDTO){
        System.out.println("START PAYMENT");
        paymentService.startPayment(cardDTO, false);
        return null;
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
