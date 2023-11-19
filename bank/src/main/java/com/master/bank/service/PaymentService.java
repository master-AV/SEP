package com.master.bank.service;

import com.master.bank.dto.*;
import com.master.bank.exception.NotValidPaymentException;
import com.master.bank.exception.NotValidPaymentRequestException;
import com.master.bank.model.Account;
import com.master.bank.model.PaymentInformation;
import com.master.bank.model.SalesAccount;
import com.master.bank.model.TransactionState;
import com.master.bank.repository.PaymentInformationRepository;
import com.master.bank.repository.SalesAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private SalesAccountRepository salesAccountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private Environment environment;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PaymentInformationRepository paymentInformationRepository;

    @Value("${pcc.url}")
    private String pccUrl;

    @Autowired
    private RestTemplate restTemplate;


    public PaymentInfoDTO requestPayment(PaymentURLRequestDTO requestDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    requestDTO.getMerchantId(), requestDTO.getMerchantPassword()));
            SalesAccount salesAccount = salesAccountRepository.findByMerchantId(requestDTO.getMerchantId());
            PaymentInformation paymentInformation = new PaymentInformation(salesAccount, requestDTO.getAmount(), requestDTO.getMerchantOrderId());
            this.paymentInformationRepository.save(paymentInformation);
            PaymentInfoDTO p = new PaymentInfoDTO(generatePaymentURL(), paymentInformation, requestDTO.getMerchantOrderId());
            System.out.println("Bank - servis - arrived");
            return p;
        }catch (AuthenticationException aut){
            System.out.println("Bank - servis - auth ex");
            throw new NotValidPaymentRequestException("Payment parameters are not valid", generateFailedUrl());
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException();
        }

    }

    private String generateFailedUrl() {
        return environment.getProperty("bank.url.failed");
    }



    private String generatePaymentURL() {
        return environment.getProperty("bank.url.success");//this.basicURL + "payment/cc";
    }

    public EndPaymentDTO startPayment(CardDTO cardDTO, boolean pccRequest) {
        if (!this.accountService.checkCardInfoValidity(cardDTO))
            throw new NotValidPaymentException("Payment is not valid");
        Account buyerAccount = accountService.getAccountByPAN(cardDTO.getPAN());
        PaymentInformation paymentInfo = paymentInformationRepository.findByPaymentId(cardDTO.getPaymentId());
        if (buyerAccount.getBankType() == paymentInfo.getBankType()) {//3.a
            TransactionState state = checkAmountOfMoney(buyerAccount, paymentInfo);
            return this.endPayment(paymentInfo, generateIdNumber10(), LocalDateTime.now(), state);
        }else if (pccRequest){
            TransactionState state = checkAmountOfMoney(buyerAccount, paymentInfo);
        }
        else //3.b
            sendRequestToPCC(cardDTO);
        return null;
    }

    private void sendRequestToPCC(CardDTO cardDTO) {
        System.out.println("SEND REQUEST TO PCC");
        try {
            URL url = new URL(pccUrl + "/req");
            System.out.println(url.getPath());
            PccRequestDTO dto = new PccRequestDTO(cardDTO, generateIdNumber10(), LocalDateTime.now());
            HttpEntity<PccRequestDTO> request = new HttpEntity<>(dto);
//            ResponseEntity<?> result = ;
            restTemplate.postForEntity("http://localhost:8082/pcc/req", request, null);
//            HttpHeaders httpHeaders = result.getHeaders();
            System.out.println("RQUEST ENDED");

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
//        } catch (URISyntaxException e) { // toURI()
//            throw new RuntimeException(e);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private long generateIdNumber10() {
        long randomNumberInRange = new Random().nextLong(0, 999999999);//
        long randomNumberInRange2 = new Random().nextLong(0, 10);//
        return randomNumberInRange+randomNumberInRange2;
    }

    private TransactionState checkAmountOfMoney(Account buyerAccount, PaymentInformation paymentInfo) {
        if (buyerAccount.getAvailableMoney() > paymentInfo.getAmount()){
            buyerAccount.setAvailableMoney(buyerAccount.getAvailableMoney()-paymentInfo.getAmount());
            buyerAccount.setReservedMoney(buyerAccount.getReservedMoney()+paymentInfo.getAmount());
            accountService.save(buyerAccount);
            return TransactionState.SUCCESSFUL;
        }
        return TransactionState.FAILED;
    }

    public void startPaymentFromPCC(PccRequestDTO pccRequestDTO) {
        this.startPayment(pccRequestDTO.getCardDTO(), true);
        System.out.println("GOT REQUEST From PCC");
        try {
            URL url = new URL(pccUrl + "/res");
            System.out.println(url.getPath());
            PccRequestDTO dto = new PccResponseDTO(pccRequestDTO, generateIdNumber10(), LocalDateTime.now());
            HttpEntity<PccRequestDTO> request = new HttpEntity<>(dto);
            ResponseEntity<?> result = restTemplate.postForEntity(url.toURI(), request, null);
            HttpHeaders httpHeaders = result.getHeaders();
            System.out.println("RQUEST ENDED");

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void endPaymentFromPCC(PccResponseDTO pccResponseDTO) {
        PaymentInformation paymentInformation = paymentInformationRepository.findByPaymentId(pccResponseDTO.getCardDTO().getPaymentId());
        this.endPayment(paymentInformation, pccResponseDTO.getAcquirerOrderId(), pccResponseDTO.getAcquirerTimestamp(), TransactionState.SUCCESSFUL);
    }

    private EndPaymentDTO endPayment(PaymentInformation paymentInformation, long acquirerOrderId,
                                     LocalDateTime acquirerTimestamp, TransactionState transactionState){
        return new EndPaymentDTO(paymentInformation, acquirerOrderId, acquirerTimestamp, transactionState);
    }
}
