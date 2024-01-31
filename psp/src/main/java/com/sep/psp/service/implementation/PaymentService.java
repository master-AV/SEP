package com.sep.psp.service.implementation;

import com.sep.psp.dto.MembershipDTO;
import com.sep.psp.dto.PaymentDTO;
import com.sep.psp.dto.QRCardDTO;
import com.sep.psp.dto.PaymentUrlDTO;

import com.sep.psp.dto.request.PaymentRequest;
import com.sep.psp.repository.AccountInformationRepository;
import com.sep.psp.repository.WalletInformationRepository;
import com.sep.psp.repository.WebshopRepository;
import com.sep.psp.service.CryptoService;
import com.sep.psp.service.EncryptionService;
import com.sep.psp.service.interfaces.IPaymentService;
import ftn.sep.db.AccountInformation;
import ftn.sep.db.WalletInformation;
import ftn.sep.db.Webshop;
import ftn.sep.dto.BPaymentDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import static com.sep.psp.utils.Constants.MEMBERSHIP_PRICE;

@Service
public class PaymentService implements IPaymentService {

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

    @Value("${apigateway.url}")
    private String apigatewayUrl;

    @Autowired
    private AccountInformationRepository accountInformationRepository;

    @Autowired
    private CryptoService cryptoService;


    private ResponseEntity<?> startPaymentForCC(int serviceId) {
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
        double amount = getPriceOfService(Long.valueOf(serviceId));
        System.out.println("Price: " + amount);
        Webshop webshop = encryptionService.decryptWebshop(webshopRepository.findByMerchantId(cryptoService.encrypt("1")));
        return new PaymentUrlDTO(amount, webshop.getMerchantId(), webshop.getMerchantPassword());
    }

    private double getPriceOfService(Long serviceId) {
        try {
            URL url = new URL(apigatewayUrl + "/offers/price/" + serviceId);

            return restTemplate.getForObject(url.toURI(), Double.class);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseEntity<?> startRequestForQR(int serviceId) {
        PaymentUrlDTO paymentUrlDTO = getPaymentUrlForService(serviceId);
        try {
            URL url = new URL(qrUrl + "req/payment");
            HttpEntity<PaymentUrlDTO> request = new HttpEntity<>(paymentUrlDTO);
            return restTemplate.postForEntity(url.toURI(), request, Object.class);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseEntity<?> startPaymentForQR(int userId, String paymentId, String merchantInformation) {
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


    public ResponseEntity<?> payWithChosenMethod(PaymentRequest paymentRequest) throws MalformedURLException, URISyntaxException {
        double price = 0.0;
        if(paymentRequest.getOfferId() == 0L) {
            price = MEMBERSHIP_PRICE;
        } else {
            price = 100;//getPriceOfService(paymentRequest.getOfferId());
        }
        PaymentDTO paymentDTO = new PaymentDTO(paymentRequest.getUserId(), price);
        ResponseEntity<?> response = callPaymentMethod(paymentRequest.getMethod(), paymentDTO);
        if(paymentRequest.getOfferId() == 0L && (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED)){
            URL url = new URL(apigatewayUrl + "/users/membership");
            MembershipDTO membershipDTO = new MembershipDTO(paymentRequest.getUserId(), paymentRequest.isSubscribedMembership());
            return restTemplate.postForEntity(url.toURI(), membershipDTO, Object.class);
        }

        return response;
    }

    @Autowired
    private WalletInformationRepository walletInformationRepository;
    private ResponseEntity<?> callPaymentMethod(String method, PaymentDTO paymentDTO) throws MalformedURLException, URISyntaxException {

        //OVDE TREBA DA BUDE SWITCH, za svaku metodu placanja, treba pozivati slicno
        if(method.equals("PAYPAL")){
            URL url = new URL(apigatewayUrl + "/paypal");
            return restTemplate.postForEntity(url.toURI(), paymentDTO, Object.class);
        } else if (method.equals("BITCOIN")){
            URL url = new URL("http://localhost:8084" + "/bitcoin");
            Webshop webshop = webshopRepository.findById(2L).orElseThrow(()-> new NotFoundException());
            WalletInformation walletInformation = walletInformationRepository.findByUserId(paymentDTO.getUserId()).orElse(null);
            BPaymentDTO bitcoinDTO = null;
            if (walletInformation != null)
                bitcoinDTO = new BPaymentDTO(paymentDTO,
                        cryptoService.decrypt(webshop.getMerchantId()), cryptoService.decrypt(webshop.getMerchantPassword()),
                        cryptoService.decrypt(walletInformation.getAccountId()), cryptoService.decrypt(walletInformation.getAccountKey()));
            else
                bitcoinDTO = new BPaymentDTO(paymentDTO, null, null, null, null);
            return restTemplate.postForEntity(url.toURI(), bitcoinDTO, Object.class);
        }
        return null;

//            case "CREDIT CARD":
//            {
//                return new ResponseEntity<>(HttpStatus.OK);
//            }
//            case "QR CODE":
//            {
//                return new ResponseEntity<>(HttpStatus.OK);
//            }
//            default: //bitcoin
//            {
//                return new ResponseEntity<>(HttpStatus.OK);
//
    }

}
