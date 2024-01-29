package com.master.bank.controller;

import com.master.bank.dto.EndPaymentDTO;
import com.master.bank.dto.PaymentInfoDTO;
import com.master.bank.dto.PaymentURLRequestDTO;
import com.master.bank.dto.QRCardDTO;
import com.master.bank.service.PaymentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/bank")
public class QRPaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private Environment environment;

    @PostMapping(value = "/qr")
    public ResponseEntity<?> requestPaymentQR(@RequestBody PaymentURLRequestDTO requestDTO,
                                              HttpServletResponse response){
        try {
            System.out.println("Bank - controller - request payment - QR");
            Map<String, Object> infoPair = paymentService.requestPaymentQR(requestDTO);
            PaymentInfoDTO info = (PaymentInfoDTO) infoPair.get("PaymentInfoDTO");
            response.addHeader("PaymentUrl", info.getPaymentUrl());
            response.addHeader("PaymentId", info.getPaymentId());
            return ResponseEntity.ok(infoPair.get("QRCode"));
        }catch (Exception ex){
            response.addHeader("ErrorUrl", environment.getProperty("bank.url.error"));
            return ResponseEntity.ok("");
        }
    }

//    @PostMapping(value = "/qr/pay")
//    public ResponseEntity<?> payQR(@RequestBody String qrCode,
//                                              HttpServletResponse response){
//        try {
//            System.out.println("Bank - controller - pay - QR");
//            String qr = QRCodeGenerator.decodeQR(qrCode);
//            System.out.println(qr);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    @PostMapping(value = "/start/payment/qr")
    @CrossOrigin(exposedHeaders = {"Location"})
    public ResponseEntity<?> payWithQR(@RequestBody QRCardDTO cardDTO){
        HttpHeaders headers = new HttpHeaders();
        try {
            System.out.println("START PAYMENT - QR");
            EndPaymentDTO endPaymentDTO = paymentService.startPayment(cardDTO);
            System.out.println(endPaymentDTO.getTransactionState());
            switch (endPaymentDTO.getTransactionState()) {
                case FAILED -> headers.add("Location", environment.getProperty("bank.url.failed"));
                case SUCCESSFUL -> headers.add("Location", environment.getProperty("bank.url.success.pay"));
                default -> headers.add("Location", environment.getProperty("bank.url.error"));
            }
            System.out.println(" -------------------- at the end --------------------");
            System.out.println(headers.get("Location"));

        }catch (UnexpectedRollbackException e){
            headers.add("Location", environment.getProperty("bank.url.success.pay"));
        }catch (Exception e){
            headers.add("Location", environment.getProperty("bank.url.error"));
        }
        return new ResponseEntity<>("", headers, HttpStatus.OK);
    }

}
