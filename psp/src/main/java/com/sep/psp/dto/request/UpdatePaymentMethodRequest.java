package com.sep.psp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePaymentMethodRequest {
    @NotBlank(message = "Name mustn't be empty")
    private String name;
    private boolean subscribed;
}
