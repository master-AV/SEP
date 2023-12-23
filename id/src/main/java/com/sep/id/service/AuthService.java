package com.sep.id.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.sep.id.dto.LoginResponse;
import com.sep.id.dto.RegistrationRequest;
import com.sep.id.dto.UserResponse;
import com.sep.id.exception.UserLockedException;
import com.sep.id.repository.RoleRepository;
import com.sep.id.repository.UserRepository;
import com.sep.id.repository.VerificationRepository;
import com.sep.id.security.FingerprintProperties;
import com.sep.id.security.FingerprintUtils;
import com.sep.id.security.JWTUtils;
import com.sep.id.security.UserPrinciple;
import ftn.sep.db.RegistrationVerification;
import ftn.sep.db.Role;
import ftn.sep.db.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

import static com.sep.id.utils.Constants.MAX_NUM_VERIFY_TRIES;
import static com.sep.id.utils.Constants.ZERO_FAILED_ATTEMPTS;


@Service
public class AuthService {
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    VerificationRepository verificationRepository;
//    @Autowired
//    private EmailService emailService;
    @Autowired
    private UserService userService;
//
//    private final KieContainer kieContainer;
//    private final KieSession kSession;

    /*@Autowired
    public AuthService(KieContainer kieContainer){
        this.kieContainer = kieContainer;
        kSession = this.kieContainer.newKieSession("cashCreditKsession");
    }*/

    public LoginResponse login(String email, String password, final HttpServletResponse response){
        Authentication authenticate;
//        email = "new.user@email.com";
//        password = "123";
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

        } catch (Exception ignored) {
            User user = userService.getVerifiedUser(email);
            return null;
        }

        User user = userService.getVerifiedUser(email);
        if(user.getRole().getRoleName().equals("ROLE_CLIENT")) {
//            Client client = (Client) user;
//            client.setUnsuccessfulLogin(0);
//            userService.save(client);
        }

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserPrinciple userPrinciple = (UserPrinciple)authenticate.getPrincipal();
        UserResponse userResponse = userPrinciple.getUser();

        String rawFingerprint = FingerprintUtils.generateRandomRawFingerprint();

        Cookie cookie = new Cookie(FingerprintProperties.FINGERPRINT_COOKIE, rawFingerprint);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(3600*4);
        response.addCookie(cookie);

        return new LoginResponse(JWTUtils.generateJWT(email, rawFingerprint), userResponse);
    }


    public void logout(HttpServletRequest request) {
        DecodedJWT jwt = JWTUtils.extractJWTFromRequest(request);
        try {
            String email = JWTUtils.extractEmailFromJWT(jwt);
            User usr = userService.getVerifiedUser(email);
        } catch (Exception ignored) {//InvalidJWTException | EntityNotFoundException
        }
    }

//    private void incrementNumberOfUnsuccessfulLogin(Client user) throws EntityNotFoundException {
//
//        if(user != null){
//            user.setUnsuccessfulLogin(user.getUnsuccessfulLogin() + 1);
//            userService.save(user);
//        }
//    }

    public void registerRequest(RegistrationRequest userRequest) {
        //User u = userService.findByEmail(userRequest.getEmail());
        //if (u != null) throw new RuntimeException("Already existing email");

        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setPassword(userRequest.getPassword());
        user.setSurname(userRequest.getSurname());
        user.setRole(roleRepository.findById(1L).orElseThrow(()->new NotFoundException("Role not found")));
        /*user.setEmail("new.user@email.com");
        user.setVerified(true);
        user.setPassword(passwordEncoder.encode("aA1*aA1*aA1*"));
        Role role = new Role();
        role.setId(1L);
        role.setRoleName("ROLE_USER");
        user.setRole(role);*/
        userService.save(user);

    }

    public void confirmPin(String email, String pin) throws UserLockedException
    //throws EntityNotFoundException, WrongVerifyTryException, UserLockedException
    {
        User user = userService.getVerifiedUser(email);

        if (!checkPin(pin, user.getPin())) {
            incrementFailedAttempts(user.getEmail());
//            logService.generateLog(LogGenerator.unuccessfulLogin(email), LogLevel.ERROR);
            throw new RuntimeException("Your security code is not accepted. Try again.");
        }
    }

    public void incrementFailedAttempts(String email) throws UserLockedException
    {
        User user = userService.getVerifiedUser(email);
        this.checkUserLocked(user.getLockedUntil(), email);

        user.setFailedAttempts(user.getFailedAttempts()+1);
        if(user.getFailedAttempts() == MAX_NUM_VERIFY_TRIES){
            user.setLockedUntil((LocalDateTime.now()).plusDays(1));
//            fireRules(user);
            userService.save(user);
//            logService.generateLog(LogGenerator.accountLockedLogin(email), LogLevel.ERROR);

            throw new UserLockedException("Your account is locked. You can login again in 24 hours.");
        }

        userService.save(user);
    }

    private void checkUserLocked(LocalDateTime lockedUntil, String email) throws UserLockedException {
        if(lockedUntil != null && lockedUntil.isAfter(LocalDateTime.now())){
//            logService.generateLog(LogGenerator.accountLockedLogin(email), LogLevel.ERROR);
            throw new UserLockedException("Your account is locked.");
        }
    }

    private boolean checkPin(String enteredPin, String pin){
        return BCrypt.checkpw(enteredPin, pin);
    }



}
