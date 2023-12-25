package com.sep.psp.dto.response;

import ftn.sep.db.Offer;
import ftn.sep.db.PaymentMethod;
import ftn.sep.dto.response.OfferResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodResponse {
    private Long id;
    private String name;
    private String img;
    private boolean subscribed;

    public PaymentMethodResponse(PaymentMethod paymentMethod) {
        this.id = paymentMethod.getId();
        this.name = paymentMethod.getName();
        this.img = paymentMethod.getImg();
        this.subscribed = paymentMethod.isSubscribed();
    }

    public static List<PaymentMethodResponse> formPaymentMethodResponses(List<PaymentMethod> paymentMethods) {
        List<PaymentMethodResponse> paymentMethodResponses = new LinkedList<>();
        paymentMethods.forEach(paymentMethod ->
                paymentMethodResponses.add(new PaymentMethodResponse(paymentMethod))
        );

        return paymentMethodResponses;
    }
}
