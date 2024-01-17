package com.sep.psp.dto;

import com.sep.psp.dto.request.PaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentUrlDTO {
    private PaymentDTO paymentDTO;
    private String merchantId;
    private String merchantPassword;
}
