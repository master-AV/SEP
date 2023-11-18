package com.master.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PccRequestDTO {
    protected CardDTO cardDTO;
    protected long acquirerOrderId;//ACQUIRER_ORDER_ID
    protected LocalDateTime acquirerTimestamp;//ACQUIRER_TIMESTAMP
}
