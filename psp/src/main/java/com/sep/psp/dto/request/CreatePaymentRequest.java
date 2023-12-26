package com.sep.psp.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static ftn.sep.util.Constants.MISSING_ID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {

    @NotNull(message=MISSING_ID)
    private Long userId;
    @NotNull(message=MISSING_ID)
    private Long offerId;
}
