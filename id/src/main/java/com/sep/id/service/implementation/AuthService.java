package com.sep.id.service.implementation;

import com.auth0.jwt.interfaces.DecodedJWT;

import com.sep.id.dto.response.LoginResponse;
import com.sep.id.dto.response.UserResponse;
import com.sep.id.exception.EntityNotFoundException;
import com.sep.id.exception.InvalidCredsException;
import com.sep.id.exception.InvalidJWTException;
import com.sep.id.exception.UserLockedException;
import com.sep.id.security.FingerprintProperties;
import com.sep.id.security.FingerprintUtils;
import com.sep.id.security.JWTUtils;
import com.sep.id.security.UserPrinciple;
import com.sep.id.service.interfaces.IAuthService;
import com.sep.id.service.interfaces.IUserService;
import ftn.sep.db.User;
import ftn.sep.dto.request.LogRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.LogLevel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

@Service

public class AuthService implements IAuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmailService emailService;
    @Autowired
    private IUserService userService;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${apigateway.url}")
    private String apigatewayUrl;

    @Override
    public void logout(HttpServletRequest request) {
        DecodedJWT jwt = JWTUtils.extractJWTFromRequest(request);
        try {
            String email = JWTUtils.extractEmailFromJWT(jwt);
            User usr = userService.getVerifiedUser(email);
        } catch (EntityNotFoundException | InvalidJWTException e) {
            throw new RuntimeException(e);
        }
    }

    public LoginResponse login(final String email, final String password, final HttpServletRequest request, final HttpServletResponse response)
            throws UserLockedException, EntityNotFoundException, InvalidCredsException, MalformedURLException, URISyntaxException {
        Authentication authenticate;

        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (Exception ignored) {
            LogRequest logRequest = new LogRequest(String.format("User with %s used invalid credentials.", email), LogLevel.INFO);
            URL log_url = new URL(apigatewayUrl + "/log");
            restTemplate.postForEntity(log_url.toURI(), logRequest, Object.class);
            throw new InvalidCredsException("Invalid credenti!");
        }

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserPrinciple userPrinciple = (UserPrinciple) authenticate.getPrincipal();
        UserResponse userResponse = userPrinciple.getUser();

        String rawFingerprint = createCookie(response);


        return new LoginResponse(JWTUtils.generateJWT(email, rawFingerprint), userResponse);
    }

    private String createCookie(HttpServletResponse response) {
        String rawFingerprint = FingerprintUtils.generateRandomRawFingerprint();

        Cookie cookie = new Cookie(FingerprintProperties.FINGERPRINT_COOKIE, rawFingerprint);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setMaxAge(3600*4);
        response.addCookie(cookie);

        return rawFingerprint;
    }

}
