package com.master.bank.controller;

import com.master.bank.dto.EndPaymentDTO;
import com.master.bank.dto.PccRequestDTO;
import com.master.bank.dto.PccResponseDTO;
import com.master.bank.service.PaymentService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/bank")
public class PCCController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private Environment environment;

    @PostMapping(value = "/pcc/req/{amount}")
    public ResponseEntity<?> payWithCCFromPCC(@RequestBody PccRequestDTO pccRequestDTO, @PathVariable double amount){
        System.out.println("START PAYMENT");
        System.out.println("PAN: " + pccRequestDTO.getCardDTO().getPAN());
        System.out.println("sec: " + pccRequestDTO.getCardDTO().getSecurityCode());
        System.out.println("name: " + pccRequestDTO.getCardDTO().getCardHolderName());
        System.out.println("date: " + pccRequestDTO.getCardDTO().getExpirationDate());
        EndPaymentDTO endPaymentDTO = paymentService.startPaymentFromPCC(pccRequestDTO, amount);
        return ResponseEntity.ok(endPaymentDTO);
    }

    @PostMapping(value = "/pcc/res")
    @CrossOrigin(exposedHeaders = {"Location"})
    public void payWithCCFromPCCRes(@RequestBody PccResponseDTO pccResponseDTO){
        System.out.println("PCC RES FUNCTION");
        EndPaymentDTO endPaymentDTO = paymentService.endPaymentFromPCC(pccResponseDTO);
        HttpHeaders headers = new HttpHeaders();
        switch (endPaymentDTO.getTransactionState()) {
            case FAILED -> headers.add("Location", environment.getProperty("bank.url.failed"));
            // TODO: at this point succ
            case SUCCESSFUL -> headers.add("Location", environment.getProperty("bank.url.success.pay"));
            default -> headers.add("Location", environment.getProperty("bank.url.error"));
        }
//        return new ResponseEntity<>("", headers, HttpStatus.OK);
    }

}
