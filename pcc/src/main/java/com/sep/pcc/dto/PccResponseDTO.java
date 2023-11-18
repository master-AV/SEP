package com.sep.pcc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PccResponseDTO extends PccRequestDTO {
    private long issuerOrderId;
    private LocalDateTime issuerTimestamp;

    public PccResponseDTO(PccRequestDTO pccRequestDTO, long issuerOrderId, LocalDateTime localDateTime) {
        this.cardDTO = pccRequestDTO.getCardDTO();
        this.acquirerOrderId = pccRequestDTO.getAcquirerOrderId();
        this.acquirerTimestamp = pccRequestDTO.getAcquirerTimestamp();
        this.issuerOrderId = issuerOrderId;
        this.issuerTimestamp = localDateTime;
    }
}
