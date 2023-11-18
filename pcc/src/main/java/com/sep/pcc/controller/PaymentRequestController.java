package com.sep.pcc.controller;

import com.sep.pcc.dto.PccRequestDTO;
import com.sep.pcc.dto.PccResponseDTO;
import com.sep.pcc.service.PaymentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/pcc")
public class PaymentRequestController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = "/req")
    public ResponseEntity<?> payWithCCRequest(@RequestBody PccRequestDTO requestDTO){
        System.out.println("PCC - req - arrived");
        this.paymentService.handlePCCRequest(requestDTO);
        return null;
    }

    @PostMapping(value = "/res")
    public ResponseEntity<?> payWithCCResponse(@RequestBody PccResponseDTO requestDTO){
        System.out.println("PCC - res - arrived");
        this.paymentService.handlePCCResponse(requestDTO);
        return null;
    }
}
