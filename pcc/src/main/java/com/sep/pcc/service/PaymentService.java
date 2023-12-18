package com.sep.pcc.service;

import com.sep.pcc.dto.CardDTO;
import com.sep.pcc.dto.PccRequestDTO;
import com.sep.pcc.dto.PccResponseDTO;
import com.sep.pcc.model.PccRequest;
import com.sep.pcc.repository.PccRequestRepository;
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
import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Value("${bank.url}")
    private String bankUrl;

    @Value("${bank2.url}")
    private String bank2Url;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PccRequestRepository pccRequestRepository;


    public void handlePCCRequest(PccRequestDTO requestDTO) {
        //validate
        PccRequest req = new PccRequest(requestDTO.getCardDTO().getPAN(), requestDTO.getAcquirerOrderId(), requestDTO.getAcquirerTimestamp());
        pccRequestRepository.save(req);
        try {
            URL url = null;
            if (requestDTO.getCardDTO().getPAN().charAt(0) == '0')
                url = new URL(bankUrl + "pcc/req");
            else
                url = new URL(bank2Url + "pcc/req");
            System.out.println("PAN: " + requestDTO.getCardDTO().getPAN());
            System.out.println("To bank: " + url.toURI());
            HttpEntity<PccRequestDTO> request = new HttpEntity<>(requestDTO);
            ResponseEntity<?> result = restTemplate.postForEntity(url.toURI(), request, CardDTO.class);
            HttpHeaders httpHeaders = result.getHeaders();
            System.out.println("RQUEST ENDED");

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) { // toURI()
            throw new RuntimeException(e);
        }
    }

    public void handlePCCResponse(PccResponseDTO requestDTO) {
        PccRequest req = this.pccRequestRepository.findByAcquirerOrderIdAndAcquirerTimestamp(requestDTO.getAcquirerOrderId(), requestDTO.getAcquirerTimestamp());
        req.setIssuerTimestamp(requestDTO.getIssuerTimestamp());
        req.setIssuerOrderId(requestDTO.getIssuerOrderId());
        this.pccRequestRepository.save(req);
        try {
            URL url = new URL(bankUrl + "pcc/res");

            HttpEntity<PccRequestDTO> request = new HttpEntity<>(requestDTO);
            ResponseEntity<?> result = restTemplate.postForEntity(url.toURI(), request, CardDTO.class);
            HttpHeaders httpHeaders = result.getHeaders();
            System.out.println("RQUEST ENDED");

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) { // toURI()
            throw new RuntimeException(e);
        }
    }
}
