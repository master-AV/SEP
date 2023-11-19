package com.sep.pcc.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PccRequest {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentId;
    private long acquirerOrderId;//ACQUIRER_ORDER_ID
    private LocalDateTime acquirerTimestamp;//ACQUIRER_TIMESTAMP
    private long issuerOrderId;
    private LocalDateTime issuerTimestamp;

    public PccRequest(String paymentId, long acquirerOrderId, LocalDateTime acquirerTimestamp) {
        this.paymentId = paymentId;
        this.acquirerOrderId = acquirerOrderId;
        this.acquirerTimestamp = acquirerTimestamp;
    }
}
