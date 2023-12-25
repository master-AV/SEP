package com.sep.id.controller;

import com.sep.id.dto.request.LoginRequest;
import com.sep.id.dto.request.VerifyRequest;
import com.sep.id.dto.response.LoginResponse;
import com.sep.id.exception.*;
import com.sep.id.service.implementation.AuthService;
import com.sep.id.service.implementation.UserService;
import com.sep.id.service.implementation.VerificationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.sep.id.utils.ErrorMessageConstants.WRONG_VERIFY_HASH;

@RestController
@RequestMapping("/id")
@Validated
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private UserService userService;

    @PostMapping(path="/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@Valid @RequestBody final LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) throws UserLockedException, InvalidCredsException, EntityNotFoundException {
        return authService.login(loginRequest.getEmail(), loginRequest.getPassword(), request, response);
    }


    @PostMapping(path = "/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(final HttpServletRequest request) {
        authService.logout(request);
    }


    @PostMapping("/send-code-again")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @NotNull(message = WRONG_VERIFY_HASH) @RequestBody String verifyHash) throws EntityNotFoundException, MailCannotBeSentException, IOException, EntityNotFoundException, MailCannotBeSentException {
        this.verificationService.generateNewSecurityCode(verifyHash);
    }

    @PutMapping("/activate-account")
    @ResponseStatus(HttpStatus.OK)
    public boolean update(@Valid @RequestBody VerifyRequest verifyRequest) throws EntityNotFoundException, WrongVerifyTryException, WrongVerifyTryException {
        return userService.activate(verifyRequest.getVerifyId(), verifyRequest.getSecurityCode());
    }
}
