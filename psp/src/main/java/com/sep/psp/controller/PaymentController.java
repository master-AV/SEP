package com.sep.psp.controller;

import com.sep.psp.dto.CardDTO;
import com.sep.psp.dto.request.PaymentRequest;
import com.sep.psp.service.AccountInformationService;
import com.sep.psp.service.implementation.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AccountInformationService accountInformationService;

    @PostMapping
//    @CrossOrigin(exposedHeaders = {"Location"})
    public ResponseEntity<?> payWithChosenMethod(@RequestBody PaymentRequest paymentRequest) throws MalformedURLException, URISyntaxException {

        return paymentService.payWithChosenMethod(paymentRequest);
    }




//    @GetMapping(value = "/payment/{serviceId}")
//    @CrossOrigin(exposedHeaders = {"Location"})
//    public ResponseEntity<?> startRequestPaymentCC(@PathVariable int serviceId){
//        System.out.println("Pay");
//        ResponseEntity<?> res = paymentService.startPaymentForCC(serviceId);
////        System.out.println(res.getHeaders().getFirst("Location"));
////        System.out.println(res.getHeaders());
//        return res;
//    }
//
//    @GetMapping(value = "/qr/{serviceId}")
//    @CrossOrigin(exposedHeaders = {"Location"})
//    public ResponseEntity<?> startRequestPaymentQR(@PathVariable int serviceId){
//        System.out.println("Pay with QR");
//        return paymentService.startRequestForQR(serviceId);
//    }

    @PostMapping(value = "/qr/pay/{userId}/{paymentId}")
    //@CrossOrigin(exposedHeaders = {"Location"})
    public ResponseEntity<?> startPaymentWithQR(@PathVariable int userId, @PathVariable String paymentId,
                                                @RequestBody String merchantInformation){
        System.out.println("Pay with QR");
        try {
            return paymentService.startPaymentForQR(userId, paymentId, merchantInformation);
        }catch (EntityNotFoundException e){
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/qr/save/{userId}")
    //@CrossOrigin(exposedHeaders = {"Location"})
    public ResponseEntity<?> saveAccountInformation(@PathVariable int userId, @RequestBody CardDTO cardDTO){
        System.out.println("Pay with QR");
        try {
            accountInformationService.saveAccountInformation(cardDTO, userId);
            return new ResponseEntity(null, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
    }
}
