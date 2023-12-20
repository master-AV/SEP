package com.sep.psp.controller;

import com.sep.psp.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/cc") // TODO: CHANGE
@CrossOrigin("http://localhost:4201")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @GetMapping(value = "/hi")
    public void hiFunc() {
        System.out.println("HEEEEY FROM API gateway");
    }

    @GetMapping(value = "/payment/{serviceId}")
    @CrossOrigin(exposedHeaders = {"Location"})
    public ResponseEntity<?> startRequestPaymentCC(@PathVariable int serviceId){
        System.out.println("Pay");
        ResponseEntity<?> res = paymentService.startPayment(serviceId);
//        System.out.println(res.getHeaders().getFirst("Location"));
//        System.out.println(res.getHeaders());
        return res;
    }

}
