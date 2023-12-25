package com.sep.psp.service.implementation;

import com.sep.psp.dto.request.UpdatePaymentMethodRequest;
import com.sep.psp.dto.response.PaymentMethodResponse;
import com.sep.psp.repository.PaymentMethodRepository;
import com.sep.psp.service.interfaces.IPaymentMethodService;
import ftn.sep.db.PaymentMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.sep.psp.dto.response.PaymentMethodResponse.formPaymentMethodResponses;

@Service
public class PaymentMethodService implements IPaymentMethodService {

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public List<PaymentMethodResponse> getSubscribed(){

        return formPaymentMethodResponses(paymentMethodRepository.getPaymentMethodBySubscribedIsTrue());
    }

    @Override
    public List<PaymentMethodResponse> getAll() {
        return formPaymentMethodResponses(paymentMethodRepository.findAll());
    }

    @Override
    public List<PaymentMethodResponse> updatePaymentMethods(List<UpdatePaymentMethodRequest> updatePaymentMethodRequests) {
       List<PaymentMethod> updated = new LinkedList<>();
       List<PaymentMethod> current = paymentMethodRepository.findAll();
       for(UpdatePaymentMethodRequest pm : updatePaymentMethodRequests) {
           current.stream()
                   .filter(item -> item.getName().equals(pm.getName()))
                   .findFirst()
                   .ifPresent(item -> item.setSubscribed(pm.isSubscribed()));
       }

       return formPaymentMethodResponses(paymentMethodRepository.saveAll(current));
       }

}
