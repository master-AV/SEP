package com.sep.id.service.implementation;

import com.sep.id.dto.response.UserResponse;
import com.sep.id.exception.*;
import com.sep.id.repository.UserRepository;
import com.sep.id.service.interfaces.IUserService;
import ftn.sep.db.RegistrationVerification;
import ftn.sep.db.Role;
import ftn.sep.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

import static com.sep.id.utils.Constants.SALT_LENGTH;
import static com.sep.id.utils.Helper.*;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private RoleService roleService;


    @Override
    public User getVerifiedUser(String email) throws EntityNotFoundException {
        return userRepository.getVerifiedUser(email)
                .orElseThrow(EntityNotFoundException::new);
    }


    @Override
    public boolean checkIfUserAlreadyExists(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public UserResponse create(
            String email,
            String name,
            String surname,
            String password,
            String confirmPassword,
            String roleName
    ) throws PasswordsDoNotMatchException, IOException, MailCannotBeSentException, EntityAlreadyExistsException {
        if (passwordsDontMatch(password, confirmPassword)) {
            throw new PasswordsDoNotMatchException();
        }

        if (this.checkIfUserAlreadyExists(email)) {
            throw new EntityAlreadyExistsException(String.format("User with email %s already exists.", email));
        }

        verificationService.create(email);
        Role role = roleService.getRoleByName(roleName);

        String salt = generateRandomString(SALT_LENGTH);
        String hashedPassword = getHash(password);
        User user = userRepository.save(
               new User(name, surname, email, false, hashedPassword, role));

        return new UserResponse(user);

    }

    @Override
    public boolean activate(String verifyId, String securityCode) throws EntityNotFoundException, WrongVerifyTryException {
        RegistrationVerification verify = verificationService.update(verifyId, securityCode);
        activateAccount(verify.getUserEmail());
        return true;
    }

    private void activateAccount(String userEmail) throws EntityNotFoundException {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(EntityNotFoundException::new);
        user.setVerified(true);
        userRepository.save(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
