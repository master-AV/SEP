package com.master.bank.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.master.bank.dto.*;
import com.master.bank.exception.NonExistentAccountException;
import com.master.bank.exception.NotValidPaymentException;
import com.master.bank.exception.NotValidPaymentRequestException;
import com.master.bank.exception.NotValidQRCodeException;
import com.master.bank.model.Account;
import com.master.bank.model.PaymentInformation;
import com.master.bank.model.SalesAccount;
import com.master.bank.model.TransactionState;
import com.master.bank.repository.PaymentInformationRepository;
import com.master.bank.repository.SalesAccountRepository;
import org.antlr.v4.runtime.misc.Pair;
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
    @Autowired
    private CryptoService cryptoService;


    public PaymentInfoDTO requestPayment(PaymentURLRequestDTO requestDTO) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    requestDTO.getMerchantId(), requestDTO.getMerchantPassword()));
            SalesAccount salesAccount = salesAccountRepository.findByMerchantId(cryptoService.encrypt(requestDTO.getMerchantId()));
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

    private String generatePaymentURLRQ() {
        return environment.getProperty("bank.url.qr.success");//this.basicURL + "payment/cc";
    }

    @Transactional()
    public EndPaymentDTO startPayment(CardDTO cardDTO) {
        PaymentInformation paymentInfo = paymentInformationRepository.findByPaymentId(cardDTO.getPaymentId())
                .orElse(null);
        try {
            if (cardDTO instanceof QRCardDTO){
                String qr = ((QRCardDTO) cardDTO).getMerchantInformation();
                if (validate(qr, paymentInfo))
                    throw new NotValidQRCodeException("QR is not valid");
            }
            if (!this.accountService.checkCardInfoValidity(cardDTO))
                throw new NotValidPaymentException("Payment is not valid");
            Account buyerAccount = accountService.getAccountByPAN(cryptoService.encrypt(cardDTO.getPAN()));//
            if (cryptoService.decrypt(buyerAccount.getPAN()).charAt(0) != (cardDTO.getPAN().charAt(0))){
                System.out.println("PCC");
                return sendRequestToPCC(cardDTO, paymentInfo.getAmount());
            }
            TransactionState state = checkAmountOfMoney(buyerAccount, paymentInfo.getAmount());
            return this.endPayment(paymentInfo, generateIdNumber10(), LocalDateTime.now(), state);
        }catch (NonExistentAccountException existentAccountException){
            return sendRequestToPCC(cardDTO, paymentInfo.getAmount());
        }catch (NotValidQRCodeException exception){
            EndPaymentDTO e = new EndPaymentDTO();
            e.setTransactionState(TransactionState.ERROR);
            return e;
        }
    }

    private boolean validate(String qr, PaymentInformation paymentInformation) {
        if (paymentInformation == null) return false;
        try {
            String dto = QRCodeGenerator.decodeQR(qr);
            System.out.println(dto);

            Gson gson = new Gson();
            QRMerchantDTO merchantDTO = gson.fromJson(dto, QRMerchantDTO.class);

            if (paymentInformation.getAmount() == merchantDTO.getAmount() &&
                    cryptoService.encrypt(merchantDTO.getRecipientPAN()).equals(paymentInformation.getAccount().getAccount().getPAN())
                    && cryptoService.encrypt(merchantDTO.getRecipientName()).equals(paymentInformation.getAccount().getAccount().getCardHolderName()))
                return true;
        }catch (Exception ex){
            return false;
        }
        return false;
    }

    public EndPaymentDTO startPaymentFromPCC(CardDTO cardDTO, double amount) {
        System.out.println("Start Payment - PCC");
//        try {
        if (!this.accountService.checkCardInfoValidity(cardDTO))
            throw new NotValidPaymentException("Payment is not valid");
        Account buyerAccount = accountService.getAccountByPAN(cryptoService.encrypt(cardDTO.getPAN()));//)
        TransactionState state = checkAmountOfMoney(buyerAccount, amount);
        return this.endPayment(null, generateIdNumber10(), LocalDateTime.now(), state);
//        }catch (NonExistentAccountException existentAccountException){
//            sendRequestToPCC(cardDTO);
//        }
//        return null;
    }

    private EndPaymentDTO sendRequestToPCC(CardDTO cardDTO, double amount) {
        System.out.println("SEND REQUEST TO PCC");
        try {
            URL url = new URL(pccUrl + "/req/" + amount);
            System.out.println(url.getPath());
            PccRequestDTO dto = new PccRequestDTO(cardDTO, generateIdNumber10(), LocalDateTime.now());
            HttpEntity<PccRequestDTO> request = new HttpEntity<>(dto);
            ResponseEntity<EndPaymentDTO> res = restTemplate.postForEntity("http://localhost:8082/pcc/req/" + amount, request, EndPaymentDTO.class);
            EndPaymentDTO endPaymentDTO = res.getBody();
            System.out.println(endPaymentDTO.getMerchantOrderId());
            System.out.println(endPaymentDTO.getPaymentId());
            System.out.println(endPaymentDTO.getTransactionState());
            System.out.println(endPaymentDTO.getAcquirerTimestamp());
            System.out.println("sendRequestToPCC - RQUEST ENDED");
            System.out.println(res.getBody());
            return endPaymentDTO;
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private long generateIdNumber10() {
        long randomNumberInRange = new Random().nextLong(0, 999999999);//
        long randomNumberInRange2 = new Random().nextLong(0, 10);//
        return randomNumberInRange+randomNumberInRange2;
    }

    private TransactionState checkAmountOfMoney(Account buyerAccount, double amount) {
        if (buyerAccount.getAvailableMoney() > amount){
            buyerAccount.setAvailableMoney(buyerAccount.getAvailableMoney()-amount);
            buyerAccount.setReservedMoney(buyerAccount.getReservedMoney()+amount);
            accountService.save(buyerAccount);
            return TransactionState.SUCCESSFUL;
        }
        return TransactionState.FAILED;
    }

    public EndPaymentDTO startPaymentFromPCC(PccRequestDTO pccRequestDTO, double amount) {
        EndPaymentDTO endPaymentDTO = this.startPaymentFromPCC(pccRequestDTO.getCardDTO(), amount);
        System.out.println("GOT REQUEST From PCC");
        try {
            URL url = new URL(pccUrl + "/res");
            System.out.println(url.getPath());
            PccRequestDTO dto = new PccResponseDTO(pccRequestDTO, generateIdNumber10(), LocalDateTime.now());
            HttpEntity<PccRequestDTO> request = new HttpEntity<>(dto);
            ResponseEntity<?> result = restTemplate.postForEntity(url.toURI(), request, null);
            HttpHeaders httpHeaders = result.getHeaders();
            System.out.println("startPaymentFromPCC - RQUEST ENDED");
//            return this.endPayment(paymentInfo, generateIdNumber10(), LocalDateTime.now(), state);
            return endPaymentDTO;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public EndPaymentDTO endPaymentFromPCC(PccResponseDTO pccResponseDTO) {
        PaymentInformation paymentInformation = paymentInformationRepository.findByPaymentId(pccResponseDTO.getCardDTO().getPaymentId())
                .orElse(null);
        return this.endPayment(paymentInformation, pccResponseDTO.getAcquirerOrderId(), pccResponseDTO.getAcquirerTimestamp(), TransactionState.SUCCESSFUL);
    }

    private EndPaymentDTO endPayment(PaymentInformation paymentInformation, long acquirerOrderId,
                                     LocalDateTime acquirerTimestamp, TransactionState transactionState){
        return new EndPaymentDTO(paymentInformation, acquirerOrderId, acquirerTimestamp, transactionState);
    }

    public Pair<PaymentInfoDTO, String> requestPaymentQR(PaymentURLRequestDTO requestDTO) {
        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    requestDTO.getMerchantId(), requestDTO.getMerchantPassword()));
            SalesAccount salesAccount = salesAccountRepository.findByMerchantId(cryptoService.encrypt(requestDTO.getMerchantId()));

            //qr
            QRMerchantDTO qrMerchantDTO = new QRMerchantDTO(
                    cryptoService.decrypt(salesAccount.getAccount().getPAN()),
                    salesAccount.getAccount().getCardHolderName(), requestDTO.getAmount());

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(qrMerchantDTO);
            String qrCode = QRCodeGenerator.generateQRCodeImage(json, 250,250);

            PaymentInformation paymentInformation = new PaymentInformation(salesAccount, requestDTO.getAmount(), requestDTO.getMerchantOrderId());
            this.paymentInformationRepository.save(paymentInformation);
            PaymentInfoDTO p = new PaymentInfoDTO(generatePaymentURLRQ(), paymentInformation, requestDTO.getMerchantOrderId());
            System.out.println("Bank - servis - arrived");
            return new Pair(p, qrCode);
        }catch (AuthenticationException aut){
            System.out.println("Bank - servis - auth ex");
            throw new NotValidPaymentRequestException("Payment parameters are not valid", generateFailedUrl());
        }catch (Exception ex){
            ex.printStackTrace();
            throw new RuntimeException();
        }
    }
}
