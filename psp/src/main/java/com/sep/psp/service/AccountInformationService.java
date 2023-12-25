package com.sep.psp.service;

import com.sep.psp.dto.CardDTO;
import com.sep.psp.repository.AccountInformationRepository;
import ftn.sep.db.AccountInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountInformationService {
    @Autowired
    private AccountInformationRepository accountInformationRepository;

    @Autowired
    private EncryptionService encryptionService;

    public void saveAccountInformation(CardDTO cardDTO, long userId){
        AccountInformation accountInformation = accountInformationRepository.findByUserId(userId).orElse(new AccountInformation());
        accountInformation.setPAN(cardDTO.getPan());
        accountInformation.setCardHolderName(cardDTO.getCardHolderName());
        accountInformation.setSecurityCode(cardDTO.getSecurityCode());
        accountInformation.setExpirationDate(cardDTO.getExpirationDate().toString());
        accountInformation.setUserId(userId);
        accountInformationRepository.save(encryptionService.encryptAccountInformation(accountInformation));
    }
}
