package com.master.bank.service;

import com.master.bank.model.Account;
import com.master.bank.model.SalesAccount;
import com.master.bank.repository.AccountRepository;
import com.master.bank.repository.SalesAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EncryptionService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    SalesAccountRepository salesAccountRepository;

    @Autowired
    CryptoService cryptoService;
    public void encryptAccount(){
        List<Account> accounts = accountRepository.findAll();
        for (Account a : accounts){
            a.setPAN(cryptoService.encrypt(a.getPAN()));
            a.setSecurityCode(cryptoService.encrypt(a.getSecurityCode()));
            a.setCardHolderName(cryptoService.encrypt(a.getCardHolderName()));
            a.setExpirationDate(cryptoService.encrypt(a.getExpirationDate().toString()));
            accountRepository.save(a);
        }
    }

    public void decryptAccount() {
        List<Account> accounts = accountRepository.findAll();
        for (Account a : accounts){
            System.out.println("------------");
            System.out.println("Acc: " + a.getCardHolderName());
            System.out.println(cryptoService.decrypt(a.getPAN()));
            System.out.println(cryptoService.decrypt(a.getExpirationDate()));
        }
    }

    public void encryptSalesAccount(){
        List<SalesAccount> accounts = salesAccountRepository.findAll();
        for (SalesAccount a : accounts){
            a.setMerchantId(cryptoService.encrypt(a.getMerchantId()));
            a.setMerchantPassword(cryptoService.encrypt(a.getMerchantPassword()));
            salesAccountRepository.save(a);
        }
    }
}
