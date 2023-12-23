package com.sep.id.security;


import com.auth0.jwt.JWT;
import com.sep.id.dto.UserResponse;
import com.sep.id.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.sep.id.security.FingerprintProperties.SECRET;
import static com.sep.id.security.JwtProperties.TOKEN_PREFIX;


public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    private final UserService userService;

    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            UserService userService
    ) {
        super(authenticationManager);
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException, ServletException {
        String header = request.getHeader(JwtProperties.HEADER_STRING);

        if (headerIsInvalid(header)) {
            chain.doFilter(request, response);
        }
        else {
            Authentication authentication = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        Authentication authentication = null;
        try {
            authentication = getUsernamePasswordAuthentication(request);
        } catch (Exception e) {// EntityNotFoundException
            e.printStackTrace();
        }
        return authentication;
    }

    private boolean headerIsInvalid(String header){

        return header == null || !header.startsWith(TOKEN_PREFIX);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JwtProperties.HEADER_STRING)
                .replace(TOKEN_PREFIX,"");

        String email = JWT.require(HMAC512(SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();

        return emailIsNotNull(email) ? getSpringAuthToken(email) : null;
    }

    private UsernamePasswordAuthenticationToken getSpringAuthToken(String email)
    {
        UserResponse userDTO = new UserResponse(userService.getVerifiedUser(email));
        return getUsernamePasswordAuthenticationToken(userDTO);
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(UserResponse userDTO) {
        UserPrinciple principal = new UserPrinciple(userDTO);

        return new UsernamePasswordAuthenticationToken(
                userDTO.getEmail(),
                principal.getPassword(),
                principal.getAuthorities()
        );
    }

    private boolean emailIsNotNull(String email){
        return email != null;
    }
}