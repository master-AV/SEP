package com.sep.id.controller;

import com.sep.id.dto.request.UserRegistrationRequest;
import com.sep.id.dto.response.UserResponse;
import com.sep.id.exception.EntityAlreadyExistsException;
import com.sep.id.exception.MailCannotBeSentException;
import com.sep.id.exception.PasswordsDoNotMatchException;
import com.sep.id.service.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody UserRegistrationRequest request) throws PasswordsDoNotMatchException, EntityAlreadyExistsException, IOException, MailCannotBeSentException {
        return userService.create(
                request.getEmail(),
                request.getName(),
                request.getSurname(),
                request.getPassword(),
                request.getConfirmPassword(),
                request.getRole()
        );
    }
}
