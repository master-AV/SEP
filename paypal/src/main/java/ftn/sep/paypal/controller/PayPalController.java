package ftn.sep.paypal.controller;

import ftn.sep.dto.PaymentDTO;
import ftn.sep.paypal.exception.PayPalPaymentException;
import ftn.sep.paypal.service.interfaces.IPayPalService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("paypal")
public class PayPalController {

    @Autowired
    private IPayPalService payPalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> createPayment(@Valid @RequestBody PaymentDTO paymentDTO)
            throws EntityNotFoundException, PayPalPaymentException {

        return payPalService.createPayment(paymentDTO.getUserId(), paymentDTO.getPrice());
    }
}
