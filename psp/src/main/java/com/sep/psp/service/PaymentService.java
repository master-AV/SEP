package com.sep.psp.service;

import com.sep.psp.dto.QRCardDTO;
import com.sep.psp.dto.PaymentUrlDTO;

import com.sep.psp.repository.AccountInformationRepository;
import com.sep.psp.repository.WebshopRepository;
import ftn.sep.db.AccountInformation;
import ftn.sep.db.Webshop;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class PaymentService {

    @Autowired
    private WebshopRepository webshopRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EncryptionService encryptionService;

    @Value("${cc.url}")
    private String ccUrl;

    @Value("${qr.url}")
    private String qrUrl;

    @Value("${webshop.url}")
    private String webshopUrl;

    @Autowired
    private AccountInformationRepository accountInformationRepository;

    @Autowired
    private CryptoService cryptoService;


    public ResponseEntity<?> startPaymentForCC(int serviceId) {
        PaymentUrlDTO paymentUrlDTO = getPaymentUrlForService(serviceId);
        try {
            URL url = new URL(ccUrl + "req/payment");
            HttpEntity<PaymentUrlDTO> request = new HttpEntity<>(paymentUrlDTO);
            return restTemplate.postForEntity(url.toURI(), request, Long.class);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private PaymentUrlDTO getPaymentUrlForService(int serviceId) {
        System.out.println("Getting the price of item: " + serviceId);
        double amount = getPriceOfService(serviceId);
        System.out.println("Price: " + amount);
        Webshop webshop = encryptionService.decryptWebshop(webshopRepository.findByMerchantId(cryptoService.encrypt("1")));
        return new PaymentUrlDTO(amount, webshop.getMerchantId(), webshop.getMerchantPassword());
    }

    private double getPriceOfService(int amount) {
        try {
            URL url = new URL(webshopUrl + "offers/price/" + amount);

            return restTemplate.getForObject(url.toURI(), Double.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<?> startRequestForQR(int serviceId) {
        PaymentUrlDTO paymentUrlDTO = getPaymentUrlForService(serviceId);
        try {
            URL url = new URL(qrUrl + "req/payment");
            HttpEntity<PaymentUrlDTO> request = new HttpEntity<>(paymentUrlDTO);
            return restTemplate.postForEntity(url.toURI(), request, Object.class);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<?> startPaymentForQR(int userId, String paymentId, String merchantInformation) {
        AccountInformation acc = accountInformationRepository.findByUserId(userId)
                .orElseThrow(()-> new EntityNotFoundException("No account information found"));
        QRCardDTO cardDTO = new QRCardDTO(encryptionService.decryptAccountInformation(acc), paymentId, merchantInformation);
        try {
            URL url = new URL(qrUrl + "start/payment");
            HttpEntity<QRCardDTO> request = new HttpEntity<>(cardDTO);
            return restTemplate.postForEntity(url.toURI(), request, Object.class);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
