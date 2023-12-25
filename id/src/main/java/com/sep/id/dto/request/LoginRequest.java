package com.sep.id.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.sep.id.utils.Constants.LEGIT_PASSWORD_REG;
import static com.sep.id.utils.ErrorMessageConstants.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = EMPTY_EMAIL)
    @Size(max = 320, message = TOO_LONG_EMAIL)
    @Email(message = WRONG_EMAIL)
    private String email;

    @NotBlank(message = WRONG_PASSWORD)
//    @Pattern(regexp = LEGIT_PASSWORD_REG, message = WRONG_PASSWORD)
//    @Size(min = 12, message = PASSWORD_NOT_LONG_ENOUGH)
    private String password;
}
