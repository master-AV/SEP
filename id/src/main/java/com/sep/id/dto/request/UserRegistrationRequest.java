package com.sep.id.dto.request;

import com.sep.id.utils.Constants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import static com.sep.id.utils.Constants.*;

@Getter
@Setter
public class UserRegistrationRequest {

    @NotNull
    @NotBlank(message = EMPTY_EMAIL)
    @Size(max = 320, message = TOO_LONG_EMAIL)
    private String email;

    @NotNull
    @NotBlank(message = WRONG_NAME)
    @Pattern(regexp = Constants.LEGIT_NAME_REG, message = WRONG_NAME)
    private String name;

    @NotNull
    @NotBlank(message = WRONG_SURNAME)
    @Pattern(regexp = Constants.LEGIT_NAME_REG, message = WRONG_SURNAME)
    private String surname;
    @NotBlank(message = WRONG_PASSWORD)
    @Pattern(regexp = Constants.LEGIT_PASSWORD_REG, message = WRONG_PASSWORD)
    private String password;

    @NotBlank(message = WRONG_PASSWORD)
    @Pattern(regexp = Constants.LEGIT_PASSWORD_REG, message = WRONG_PASSWORD)
    private String confirmPassword;

    @NotNull(message = WRONG_ROLE)
    private String role;

    public UserRegistrationRequest(
            String email,
            String name,
            String surname,
            String password,
            String confirmPassword,
            String role
    ) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.role = role;
    }
}
