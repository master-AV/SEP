package ftn.sep.bitcoin.controller;

import ftn.sep.bitcoin.dto.response.MessageResponse;
import ftn.sep.bitcoin.service.PaymentService;
import ftn.sep.dto.BPaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("bitcoin")
public class BitcoinController {

//    @PostMapping
//    @ResponseStatus(HttpStatus.OK)
//    public MessageResponse createPayment(@RequestBody CreatePaymentRequest createPaymentRequest){
//        return new MessageResponse("Bitcoin payment");
//    }

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> createPayment(@RequestBody BPaymentDTO paymentDTO){
        return paymentService.pay(paymentDTO);
    }

}
