package ftn.sep.bitcoin.controller;

import ftn.sep.bitcoin.dto.response.MessageResponse;
import ftn.sep.dto.request.CreatePaymentRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("bitcoin")
@CrossOrigin("http://localhost:4201")
public class BitcoinController {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse createPayment(@RequestBody CreatePaymentRequest createPaymentRequest){
        return new MessageResponse("Bitcoin payment");
    }
}
