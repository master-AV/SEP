package ftn.sep.paypal.exception;


public class PayPalPaymentException extends AppException {
    private static final String PAYPAL_PAYMENT_EXCEPTION = "Payment cannot be realized, something went wrong.";


    public PayPalPaymentException() {
        super(PAYPAL_PAYMENT_EXCEPTION);
    }

    public PayPalPaymentException(String message) {
        super(message);
    }

}
