package com.sep.pcc.controller;

import com.sep.pcc.dto.PccRequestDTO;
import com.sep.pcc.dto.PccResponseDTO;
import com.sep.pcc.service.PaymentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pcc")
public class PaymentRequestController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = "/req/{amount}")
    public ResponseEntity<?> payWithCCRequest(@RequestBody PccRequestDTO requestDTO, @PathVariable double amount){
        System.out.println("PCC - req - arrived");
        return this.paymentService.handlePCCRequest(requestDTO, amount);// transactionState=SUCCESSFUL,
    }

    @PostMapping(value = "/res")
    @CrossOrigin(exposedHeaders = {"Location"})
    public ResponseEntity<?> payWithCCResponse(@RequestBody PccResponseDTO requestDTO){
        System.out.println("PCC - res - arrived");
        return this.paymentService.handlePCCResponse(requestDTO);
    }
}
