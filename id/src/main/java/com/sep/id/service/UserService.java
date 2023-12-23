package com.sep.id.service;

import com.sep.id.dto.UserResponse;
import com.sep.id.exception.EntityNotFoundException;
import com.sep.id.exception.WrongVerifyTryException;
import com.sep.id.repository.UserRepository;
import ftn.sep.db.RegistrationVerification;
import ftn.sep.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationService verificationService;

    public User getVerifiedUser(String email)  {
        return userRepository.getVerifiedUser(email)
                .orElseThrow(() -> new RuntimeException("User is not found."));// EntityNotFoundException
    }

    public UserResponse create(String email, String name, String surname, String password, String confirmPassword, String role) {
        return null;
    }

    public boolean activate(String verifyId, int securityCode) throws WrongVerifyTryException, EntityNotFoundException { //  throws WrongVerifyTryException, EntityNotFoundException
        RegistrationVerification verify = verificationService.update(verifyId, securityCode);
        User regularUser = this.findByEmail(verify.getUserEmail());
        regularUser.setVerified(true);
        userRepository.save(regularUser);
        return true;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User is not found."));
    }
}
