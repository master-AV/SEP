package ftn.sep.webshop.service.interfaces;

import ftn.sep.db.User;
import ftn.sep.webshop.dto.response.UserResponse;
import ftn.sep.webshop.exception.*;

import java.io.IOException;

public interface IUserService {
    User getVerifiedUser(String email) throws EntityNotFoundException;
    UserResponse create(
            String email,
            String name,
            String surname,
            String password,
            String confirmPassword,
            String role
    ) throws EntityAlreadyExistsException, PasswordsDoNotMatchException, IOException, MailCannotBeSentException;
    boolean checkIfUserAlreadyExists(String email);
    boolean activate(String verifyId, String securityCode) throws EntityNotFoundException, WrongVerifyTryException;
    User save(User user);
}
