package com.sep.qr.service;

import com.sep.qr.dto.PaymentInfoWithMerchantInfo;
import com.sep.qr.dto.PaymentUrlDTO;
import com.sep.qr.dto.QRCardDTO;
import com.sep.qr.dto.WebshopInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;

@Service
public class QRPaymentService {

    private RestTemplate restTemplate;

    private HttpHeaders headers;

    @Value("${bank.url}")
    private String bankUrl;

    public QRPaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    public PaymentInfoWithMerchantInfo requestPaymentURL(PaymentUrlDTO dto) {
        // 1
        try {
            URL url = new URL(bankUrl + "qr");

            WebshopInformation info = new WebshopInformation();

            info.setMerchantId(dto.getMerchantId());
            info.setMerchantPassword(dto.getMerchantPassword());
            info.setAmount(dto.getAmount());
            info.setMerchantTimestamp(LocalDateTime.now());

            HttpEntity<WebshopInformation> request = new HttpEntity<>(info);

            ResponseEntity<String> result = restTemplate.postForEntity(url.toURI(), request, String.class);
            HttpHeaders httpHeaders = result.getHeaders();
            System.out.println("----------- qr code ---------");
            System.out.println(String.valueOf(result.getBody()));
            PaymentInfoWithMerchantInfo p = new PaymentInfoWithMerchantInfo(String.valueOf(result.getBody()));
            if (httpHeaders.getFirst("ErrorUrl") != null)
                p.setRedirectUrl(httpHeaders.getFirst("ErrorUrl"));
            else if (httpHeaders.getFirst("FailedUrl") != null)
                p.setRedirectUrl(httpHeaders.getFirst("FailedUrl"));
            else {
                p.setRedirectUrl(httpHeaders.getFirst("PaymentUrl"));
                p.setPaymentId(httpHeaders.getFirst("PaymentId"));
            }
            return p;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) { // toURI()
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<?> startPayment(QRCardDTO cardInfo) {
        try {
            URL url = new URL(bankUrl + "start/payment/qr");

            HttpEntity<QRCardDTO> request = new HttpEntity<>(cardInfo);
            return restTemplate.postForEntity(url.toURI(), request, QRCardDTO.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) { // toURI()
            throw new RuntimeException(e);
        }
    }

//    public ResponseEntity<?> startPaymentWithCC(CardDTO cardInfo) {
//        // 3
//        try {
//            URL url = new URL(bankUrl + "start/payment");
//
//            HttpEntity<CardDTO> request = new HttpEntity<>(cardInfo);
//            return restTemplate.postForEntity(url.toURI(), request, CardDTO.class);
//        } catch (MalformedURLException e) {
//            throw new RuntimeException(e);
//        } catch (URISyntaxException e) { // toURI()
//            throw new RuntimeException(e);
//        }
//    }
}
