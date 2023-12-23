package com.sep.psp.service.interfaces;

import com.sep.psp.dto.request.UpdatePaymentMethodRequest;
import com.sep.psp.dto.response.PaymentMethodResponse;

import java.util.List;

public interface IPaymentMethodService {

    List<PaymentMethodResponse> getSubscribed();
    List<PaymentMethodResponse> getAll();

    List<PaymentMethodResponse> updatePaymentMethods(List<UpdatePaymentMethodRequest> updatePaymentMethodRequests);

}
