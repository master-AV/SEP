package ftn.sep.webshop.controller;

import ftn.sep.dto.MembershipDTO;
import ftn.sep.webshop.dto.request.UserRegistrationRequest;
import ftn.sep.webshop.dto.request.VerifyRequest;
import ftn.sep.webshop.dto.response.UserResponse;
import ftn.sep.webshop.exception.*;
import ftn.sep.webshop.service.interfaces.IUsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private IUsersService userService;

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

    @PutMapping("/activate-account")
    @ResponseStatus(HttpStatus.OK)
    public boolean update(@Valid @RequestBody VerifyRequest verifyRequest) throws EntityNotFoundException, WrongVerifyTryException, EntityNotFoundException {
        return userService.activate(verifyRequest.getVerifyId(), verifyRequest.getSecurityCode());
    }

    @PostMapping("/membership")
    @ResponseStatus(HttpStatus.OK)
    public void updateMembership(@RequestBody MembershipDTO membershipDTO) throws EntityNotFoundException {
        userService.updateMembership(membershipDTO.getUserId(), membershipDTO.isSubscription(), membershipDTO.getPaymentMethod());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> getAll() {
        return userService.getAllWithRoleUser();
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getById(@PathVariable Long userId) throws EntityNotFoundException {
        return new UserResponse(userService.getById(userId));
    }
}
