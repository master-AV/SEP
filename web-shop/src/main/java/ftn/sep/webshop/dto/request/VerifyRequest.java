package ftn.sep.webshop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static ftn.sep.webshop.util.Constants.WRONG_SECURITY_CODE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyRequest {
    @NotBlank(message = WRONG_SECURITY_CODE)
    @NotNull(message = WRONG_SECURITY_CODE)
    private String verifyId;

    @NotNull(message = WRONG_SECURITY_CODE)
    private String securityCode;
}
