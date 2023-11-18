package com.master.credit.card.service;

import com.master.credit.card.dto.CardDTO;
import com.master.credit.card.dto.PaymentInfo;
import com.master.credit.card.dto.PaymentUrlDTO;
import com.master.credit.card.model.WebshopInformation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.*;
import java.time.LocalDateTime;

@Service
public class CCPaymentService {

    private RestTemplate restTemplate;

    private HttpHeaders headers;

    @Value("${bank.url}")
    private String bankUrl;

    public CCPaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public PaymentInfo requestPaymentURL(PaymentUrlDTO dto) {
        // 1
        try {
            URL url = new URL(bankUrl + "request");

            WebshopInformation info = new WebshopInformation();

            info.setMerchantId(dto.getMerchantId());
            info.setMerchantPassword(dto.getMerchantPassword());
            info.setAmount(dto.getAmount());
            info.setMerchantTimestamp(LocalDateTime.now());

            HttpEntity<WebshopInformation> request = new HttpEntity<>(info);

            ResponseEntity<?> result = restTemplate.postForEntity(url.toURI(), request, WebshopInformation.class);
            HttpHeaders httpHeaders = result.getHeaders();
            System.out.println(httpHeaders.getFirst("PaymentUrl"));
            System.out.println(httpHeaders.getFirst("PaymentId"));
            System.out.println(httpHeaders.getFirst("ErrorUrl"));
            System.out.println(httpHeaders.getFirst("FailedUrl"));
            PaymentInfo p = new PaymentInfo();
            if (httpHeaders.getFirst("ErrorUrl") != null)
                p.setRedirectUrl(httpHeaders.getFirst("ErrorUrl"));
            else if (httpHeaders.getFirst("FailedUrl") != null)
                p.setRedirectUrl(httpHeaders.getFirst("FailedUrl"));
            else {
                p.setPaymentId(httpHeaders.getFirst("PaymentUrl"));
                p.setPaymentId(httpHeaders.getFirst("PaymentId"));
            }
            return p;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) { // toURI()
            throw new RuntimeException(e);
        }
    }

    public void startPaymentWithCC(CardDTO cardInfo) {
        // 3
        try {
            URL url = new URL(bankUrl + "start/payment");

            HttpEntity<CardDTO> request = new HttpEntity<>(cardInfo);
            ResponseEntity<?> result = restTemplate.postForEntity(url.toURI(), request, CardDTO.class);
            HttpHeaders httpHeaders = result.getHeaders();

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) { // toURI()
            throw new RuntimeException(e);
        }
    }
}
