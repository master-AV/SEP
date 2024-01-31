package com.master.bank.service;

import com.master.bank.dto.CardDTO;
import com.master.bank.exception.NonExistentAccountException;
import com.master.bank.model.Account;
import com.master.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CryptoService cryptoService;

    public boolean checkCardInfoValidity(CardDTO cardDTO){
        Account account = getAccountByPAN(cryptoService.encrypt(cardDTO.getPAN()));
        if (cryptoService.encrypt(cardDTO.getPAN()).equals(account.getPAN()) &&
            cryptoService.encrypt(cardDTO.getSecurityCode()).equals(account.getSecurityCode()) &&
            cryptoService.encrypt(cardDTO.getCardHolderName()).equals(account.getCardHolderName())
                &&  cryptoService.encrypt(cardDTO.getExpirationDate().toString()).equals(account.getExpirationDate()))
            return true;
        return false;
    }

    public Account getAccountById(long id){return accountRepository.findById(id)
            .orElseThrow(() -> new NonExistentAccountException("Account with id: " + id + " does not exists"));}
    public Account getAccountByPAN(String PAN){return accountRepository.findByPAN(PAN)
            .orElseThrow(() -> new NonExistentAccountException("Account with PAN: " + PAN + " does not exists"));}

    public Account getSalesAccountByPaymentId(long id){return accountRepository.findById(id)
            .orElseThrow(() -> new NonExistentAccountException("Account with id: " + id + " does not exists"));}

    public void save(Account account) {
        this.accountRepository.save(account);
    }
}
