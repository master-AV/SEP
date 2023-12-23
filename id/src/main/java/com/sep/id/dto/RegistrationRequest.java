package com.sep.id.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegistrationRequest {

    private String email;

    @NotBlank
    @Pattern(regexp = "[A-ZČĆŠĐŽ][a-zčćšđž]{2,20}",
            message = "Name should contain just letters, and start with a capital letter")
    private String name;

    @NotBlank
    @Pattern(regexp = "[A-ZČĆŠĐŽ][a-zčćšđž]{2,20}",
            message = "Name should contain just letters, and start with a capital letter")
    private String surname;


    @NotBlank
    private String password;

}
