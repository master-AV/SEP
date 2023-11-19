package com.sep.qr.controller;

import com.sep.qr.response.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("qr")
@CrossOrigin("http://localhost:4201")
public class QRController {

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse createPayment(){
        return new MessageResponse("QR payment response");
    }

}
