package com.sep.qr.controller;

import com.sep.qr.dto.PaymentInfoWithMerchantInfo;
import com.sep.qr.dto.PaymentUrlDTO;
import com.sep.qr.dto.QRCardDTO;
import com.sep.qr.response.MessageResponse;
import com.sep.qr.service.QRPaymentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequestMapping("qr-code")
@CrossOrigin("http://localhost:4201")
public class QRController {

    @Autowired
    private QRPaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse createPayment(){
        return new MessageResponse("QR payment response");
    }

    @PostMapping(value = "/request", consumes = "application/json")
    @CrossOrigin(exposedHeaders = {"Location"})
    public ResponseEntity<?> requestPaymentURL(@RequestBody PaymentUrlDTO dto,
                                               HttpServletResponse response) throws URISyntaxException {
        PaymentInfoWithMerchantInfo info = paymentService.requestPaymentURL(dto);
        HttpHeaders headers = new HttpHeaders();
        System.out.println(info.getRedirectUrl());
        if (info.getPaymentId() != null) info.setRedirectUrl(info.getRedirectUrl()+ "/" + info.getPaymentId());
        headers.add("Location", info.getRedirectUrl());
        return new ResponseEntity(info, headers, HttpStatus.OK);
    }

    @PostMapping(value = "/start/payment", consumes = "application/json")
    @CrossOrigin(exposedHeaders = {"Location"})
    public ResponseEntity<?> startPaymentWithCC(@RequestBody QRCardDTO cardInfo){
        System.out.println("PAN: " + cardInfo.getPan());
        System.out.println("sec: " + cardInfo.getSecurityCode());
        System.out.println("name: " + cardInfo.getCardHolderName());
        System.out.println("date: " + cardInfo.getExpirationDate());
        System.out.println("pay id: " + cardInfo.getPaymentId());
        ResponseEntity<?> res =  paymentService.startPayment(cardInfo);
        return res;
    }
}
