package com.sep.api.gateway.service;

import com.sep.api.gateway.repository.UserRepository;
import ftn.sep.db.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //@Autowired
    //private VerificationService verificationService;

    public User getVerifiedUser(String email)  {
        return userRepository.getVerifiedUser(email)
                .orElseThrow(() -> new RuntimeException("User is not found."));// EntityNotFoundException
    }

    public UserResponse create(String email, String name, String surname, String password, String confirmPassword, String role) {
        return null;
    }

    public boolean activate(String verifyId, int securityCode) { //  throws WrongVerifyTryException, EntityNotFoundException
        //RegistrationVerification verify = verificationService.update(verifyId, securityCode);
        //clientService.activateAccount(verify.getUserEmail());
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
