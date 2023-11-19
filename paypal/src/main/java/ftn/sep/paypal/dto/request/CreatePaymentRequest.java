package ftn.sep.paypal.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import static ftn.sep.paypal.util.MessagesConstants.MISSING_ID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {

    @NotNull(message=MISSING_ID)
    private Long userId;
    @NotNull(message=MISSING_ID)
    private Long offerId;
    private boolean yearly;
}
