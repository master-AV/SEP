package com.sep.id.controller;

import com.sep.id.dto.*;
import com.sep.id.exception.EntityNotFoundException;
import com.sep.id.exception.MailCannotBeSentException;
import com.sep.id.exception.UserLockedException;
import com.sep.id.exception.WrongVerifyTryException;
import com.sep.id.service.AuthService;
import com.sep.id.service.UserService;
import com.sep.id.service.VerificationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.sep.id.utils.ErrorMessageConstants.WRONG_VERIFY_HASH;

@RestController
@RequestMapping("/id")
@Validated
@CrossOrigin("http://localhost:4200")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private UserService userService;

    @PostMapping(path="/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@Valid @RequestBody final LoginRequest loginRequest, HttpServletResponse response) {
        return authService.login(loginRequest.getEmail(), loginRequest.getPassword(), response);
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> addUser(@Valid @RequestBody RegistrationRequest userRequest, HttpServletRequest request) {
        try {
            authService.registerRequest(userRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }


    @PostMapping(path = "/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(final HttpServletRequest request) {
        authService.logout(request);
    }


    @PutMapping("/confirm-pin")
    @ResponseStatus(HttpStatus.OK)
    public void confirmPin(@Valid @RequestBody final ConfirmPinRequest confirmPinRequest) throws UserLockedException {
        authService.confirmPin(confirmPinRequest.getEmail(), confirmPinRequest.getPin());
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
