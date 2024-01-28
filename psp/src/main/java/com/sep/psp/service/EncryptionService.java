package com.sep.psp.service;

import com.sep.psp.repository.AccountInformationRepository;
import com.sep.psp.repository.WalletInformationRepository;
import com.sep.psp.repository.WebshopRepository;
import ftn.sep.db.AccountInformation;
import ftn.sep.db.WalletInformation;
import ftn.sep.db.Webshop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EncryptionService {

    @Autowired
    AccountInformationRepository accountRepository;

    @Autowired
    CryptoService cryptoService;
    @Autowired
    private WebshopRepository webshopRepository;

    @Autowired
    private WalletInformationRepository walletInformationRepository;

    public void encryptAccountInformation(){
        List<AccountInformation> accounts = accountRepository.findAll();
        for (AccountInformation a : accounts){
            a.setPAN(cryptoService.encrypt(a.getPAN()));
            a.setSecurityCode(cryptoService.encrypt(a.getSecurityCode()));
            a.setCardHolderName(cryptoService.encrypt(a.getCardHolderName()));
            a.setExpirationDate(cryptoService.encrypt(a.getExpirationDate()));
            accountRepository.save(a);
        }
    }

    public void encryptWebshops(){
        List<Webshop> webshops = webshopRepository.findAll();
        for (Webshop webshop : webshops){
            webshop.setMerchantId(cryptoService.encrypt(webshop.getMerchantId()));
            webshop.setMerchantPassword(cryptoService.encrypt(webshop.getMerchantPassword()));
            webshopRepository.save(webshop);
        }
    }

    public void decryptAccountInformation() {
        List<AccountInformation> accounts = accountRepository.findAll();
        for (AccountInformation a : accounts){
            System.out.println("------------");
            System.out.println("Acc: " + a.getCardHolderName());
            System.out.println(cryptoService.decrypt(a.getPAN()));
        }
    }

    public AccountInformation decryptAccountInformation(AccountInformation a) {
        a.setPAN(cryptoService.decrypt(a.getPAN()));
        a.setSecurityCode(cryptoService.decrypt(a.getSecurityCode()));
        a.setCardHolderName(cryptoService.decrypt(a.getCardHolderName()));
        a.setExpirationDate(cryptoService.decrypt(a.getExpirationDate()));
        return a;
    }

    public Webshop decryptWebshop(Webshop webshop) {
        webshop.setMerchantId(cryptoService.decrypt(webshop.getMerchantId()));
        webshop.setMerchantPassword(cryptoService.decrypt(webshop.getMerchantPassword()));
        return webshop;
    }

    public AccountInformation encryptAccountInformation(AccountInformation accountInformation) {
        accountInformation.setPAN(cryptoService.encrypt(accountInformation.getPAN()));
        accountInformation.setSecurityCode(cryptoService.encrypt(accountInformation.getSecurityCode()));
        accountInformation.setCardHolderName(cryptoService.encrypt(accountInformation.getCardHolderName()));
        accountInformation.setExpirationDate(cryptoService.encrypt(accountInformation.getExpirationDate()));
        return accountInformation;
    }

    public void encryptWalletInformation() {
        List<WalletInformation> accounts = walletInformationRepository.findAll();
        for (WalletInformation wallet : accounts){
            wallet.setAccountId(cryptoService.encrypt(wallet.getAccountId()));
            wallet.setAccountKey(cryptoService.encrypt(wallet.getAccountKey()));
            walletInformationRepository.save(wallet);
        }
    }

    public WalletInformation encryptWalletInformation(WalletInformation walletInformation) {
        walletInformation.setAccountId(cryptoService.encrypt(walletInformation.getAccountId()));
        walletInformation.setAccountKey(cryptoService.encrypt(walletInformation.getAccountKey()));
        return walletInformation;
    }
}
