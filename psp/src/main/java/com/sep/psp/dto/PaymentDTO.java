package com.sep.psp.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
//@AllArgsConstructor
public class PaymentDTO extends ftn.sep.dto.PaymentDTO {
    public PaymentDTO(Long userId, double price) {
        this.userId = userId;
        this.price = price;
    }

//    private Long userId;
//    private double price;
}
