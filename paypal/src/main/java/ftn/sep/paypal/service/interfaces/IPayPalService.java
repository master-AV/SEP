package ftn.sep.paypal.service.interfaces;

import ftn.sep.paypal.exception.PayPalPaymentException;
import jakarta.persistence.EntityNotFoundException;

import java.util.Map;

public interface IPayPalService {
    Map<String, String> createPayment(
            Long userId,
            double price
    ) throws EntityNotFoundException, PayPalPaymentException;
}
