package com.sep.psp.controller;

import com.sep.psp.dto.request.UpdatePaymentMethodRequest;
import com.sep.psp.dto.response.PaymentMethodResponse;
import com.sep.psp.service.interfaces.IPaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/payment-method")
public class PaymentMethodController {

    @Autowired
    private IPaymentMethodService paymentMethodService;

    @GetMapping("/subscribed")
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentMethodResponse> getSubscribed() {

        return paymentMethodService.getSubscribed();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentMethodResponse> getAll() {

        return paymentMethodService.getAll();
    }

    @PostMapping("/subscribed/update")
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentMethodResponse> updatePaymentMethods(@RequestBody List<UpdatePaymentMethodRequest> requests) {

        return paymentMethodService.updatePaymentMethods(requests);
    }
}
