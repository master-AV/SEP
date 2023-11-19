package ftn.sep.paypal.service.implementation;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import ftn.sep.dto.response.OfferResponse;
import ftn.sep.paypal.exception.PayPalPaymentException;
import ftn.sep.paypal.service.interfaces.IPayPalService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayPalService implements IPayPalService {

    @Value("${paypal.client.id}")
    private String clientId;
    @Value("${paypal.client.secret}")
    private String clientSecret;
    @Value("${paypal.mode}")
    private String mode;

    @Override
    public Map<String, String> createPayment(Long userId, Long offerId, boolean yearly) throws PayPalPaymentException {
        double price = getPrice(offerId, yearly);
        System.out.println(offerId);
        Amount amount = createAmount(200);
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(createTransaction(amount));

        Payer payer = createPayer();
        Payment payment = createPayment(payer, transactions);
        payment.setRedirectUrls(createRedirectUrls(userId));

        return createResponse(payment);
    }

    private double getPrice(Long offerId, boolean yearly) {
        System.out.println(offerId);
        String url = String.format("http://localhost:8080/offers/%d", offerId);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<OfferResponse> offerResponse = restTemplate.getForEntity(url, OfferResponse.class);

        return yearly ? offerResponse.getBody().getYearlyPrice() : offerResponse.getBody().getMonthlyPrice();
    }


    private Payer createPayer() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        return payer;
    }

    private Payment createPayment(Payer payer, List<Transaction> transactions) {
        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        return payment;
    }

    private Amount createAmount(double price) {
        Amount amount = new Amount();
        amount.setCurrency("EUR");
        amount.setTotal(String.valueOf(price));

        return amount;
    }


    private Transaction createTransaction(Amount amount) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);

        return transaction;
    }

    private RedirectUrls createRedirectUrls(Long offerId) {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:4201/psp/payment");
        redirectUrls.setReturnUrl(String.format("%s/%s/process", "http://localhost:4201/psp/success", offerId));

        return redirectUrls;
    }

    private Map<String, String> createResponse(Payment payment)
            throws PayPalPaymentException
    {
        Map<String, String> response = new HashMap<>();
        try {
            populateResponseWithLink(payment, response);
        } catch (PayPalRESTException e) {
            throw new PayPalPaymentException();
        }

        return response;
    }

    private Map<String, String> populateResponseWithLink(
            Payment payment,
            Map<String, String> response
    ) throws PayPalRESTException {
        Payment createdPayment;
        String redirectUrl = "";
        APIContext context = new APIContext(clientId, clientSecret, mode);
        createdPayment = payment.create(context);
        if (createdPayment!=null){
            List<Links> links = createdPayment.getLinks();
            for (Links link:links) {
                if(link.getRel().equals("approval_url")){
                    redirectUrl = link.getHref();
                    break;
                }
            }
            response.put("status", "success");
            response.put("redirectUrl", redirectUrl);
        }

        return response;
    }
}
