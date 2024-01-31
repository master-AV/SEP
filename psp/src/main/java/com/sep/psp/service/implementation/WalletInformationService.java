package com.sep.psp.service.implementation;

import com.sep.psp.dto.CardDTO;
import com.sep.psp.dto.request.WalletDTO;
import com.sep.psp.repository.AccountInformationRepository;
import com.sep.psp.repository.WalletInformationRepository;
import com.sep.psp.service.EncryptionService;
import ftn.sep.db.AccountInformation;
import ftn.sep.db.WalletInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletInformationService {
    @Autowired
    private WalletInformationRepository walletInformationRepository;

    @Autowired
    private EncryptionService encryptionService;

    public void saveWalletInformation(WalletDTO walletDTO) {
        WalletInformation walletInformation = walletInformationRepository.findByUserId(walletDTO.getUserId()).orElse(new WalletInformation());
        walletInformation.setAccountId(walletDTO.getAccountId());
        walletInformation.setAccountKey(walletDTO.getAccountKey());
        walletInformation.setUserId(walletDTO.getUserId());
        walletInformationRepository.save(encryptionService.encryptWalletInformation(walletInformation));
    }
}
