package com.master.bank.dto;

import com.master.bank.model.PaymentInformation;
import com.master.bank.model.TransactionState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EndPaymentDTO {
    private TransactionState transactionState; // TODO: take care of state
    private String paymentId;
    private long merchantOrderId;
    private long acquirerOrderId;
    private LocalDateTime acquirerTimestamp;

    public EndPaymentDTO(PaymentInformation paymentInformation, long acquirerOrderId,
                         LocalDateTime acquirerTimestamp, TransactionState transactionState) {
        this.transactionState = transactionState;
        if (paymentInformation != null){
            this.paymentId = paymentInformation.getPaymentId();
            this.merchantOrderId = paymentInformation.getMerchantOrderId();
        }
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
    }
}
