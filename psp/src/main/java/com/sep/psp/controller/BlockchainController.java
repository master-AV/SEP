package com.sep.psp.controller;

import com.sep.psp.dto.CardDTO;
import com.sep.psp.dto.request.WalletDTO;
import com.sep.psp.service.implementation.WalletInformationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/payment")
public class BlockchainController {

    @Autowired
    private WalletInformationService walletInformationService;
    @PostMapping(value = "/add-wallet")
    public ResponseEntity<?> saveWalletInformation(@RequestBody WalletDTO walletDTO){
        System.out.println("Pay with QR");
        try {
            walletInformationService.saveWalletInformation(walletDTO);
            return new ResponseEntity(null, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
    }
}
