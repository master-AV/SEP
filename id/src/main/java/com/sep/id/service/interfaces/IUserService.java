package com.sep.id.service.interfaces;


import com.sep.id.dto.response.UserResponse;
import com.sep.id.exception.*;
import ftn.sep.db.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public interface IUserService {
    User getVerifiedUser(String email) throws EntityNotFoundException;
    UserResponse create(
            String email,
            String name,
            String surname,
            String password,
            String confirmPassword,
            String role
    ) throws EntityAlreadyExistsException, PasswordsDoNotMatchException, IOException, MailCannotBeSentException, URISyntaxException;
    boolean checkIfUserAlreadyExists(String email);
    boolean activate(String verifyId, String securityCode) throws EntityNotFoundException, WrongVerifyTryException;
    User save(User user);
}
