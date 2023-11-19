package com.sep.psp.service;

import com.sep.psp.dto.PaymentUrlDTO;
import com.sep.psp.model.Webshop;
import com.sep.psp.repository.WebshopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

    @Value("${cc.url}")
    private String ccUrl;

    @Value("${webshop.url}")
    private String webshopUrl;

    public ResponseEntity<?> startPayment(int serviceId) {
        System.out.println("Getting the price of item: " + serviceId);
        double amount = getPriceOfService(serviceId);
        System.out.println("Price: " + amount);
        Webshop webshop = webshopRepository.findByMerchantId("1");
        PaymentUrlDTO paymentUrlDTO = new PaymentUrlDTO(amount, webshop.getMerchantId(), webshop.getMerchantPassword());
        try {
            URL url = new URL(ccUrl + "req/payment");

//            PccRequestDTO dto = new PccResponseDTO(pccRequestDTO, generateIdNumber10(), LocalDateTime.now());
            HttpEntity<PaymentUrlDTO> request = new HttpEntity<>(paymentUrlDTO);
            return restTemplate.postForEntity(url.toURI(), request, Long.class);
//            HttpHeaders httpHeaders = result.getHeaders();
//            System.out.println("RQUEST ENDED");
//            return result;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private double getPriceOfService(int amount) {
        try {
            URL url = new URL(webshopUrl + "offers/" + amount);

            return restTemplate.getForObject(url.toURI(), Double.class);
//            HttpHeaders httpHeaders = result.getHeaders();
//            System.out.println("RQUEST ENDED");
//            return result;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
