package ftn.sep.webshop.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import ftn.sep.db.User;
import ftn.sep.webshop.dto.response.UserResponse;
import ftn.sep.webshop.exception.EntityNotFoundException;
import ftn.sep.webshop.exception.FingerprintCookieNotFoundException;
import ftn.sep.webshop.exception.InvalidJWTException;
import ftn.sep.webshop.service.implementation.UsersService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static ftn.sep.webshop.security.JwtProperties.HEADER_STRING;
import static ftn.sep.webshop.security.JwtProperties.TOKEN_PREFIX;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UsersService userService;

    public JwtAuthenticationFilter(UsersService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (!headerIsInvalid(request.getHeader(HEADER_STRING))) {
            Authentication authentication = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private boolean headerIsInvalid(String header) {
        return header == null || !header.startsWith(TOKEN_PREFIX) || header.equals(TOKEN_PREFIX);
    }

    private Authentication getAuthentication(HttpServletRequest request) {
        try {
            return getUsernamePasswordAuthentication(request);
        } catch (EntityNotFoundException | InvalidJWTException | FingerprintCookieNotFoundException ignored) {
            return null;
        }
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) throws EntityNotFoundException, FingerprintCookieNotFoundException, InvalidJWTException {
        DecodedJWT jwt = JWTUtils.extractJWTFromRequest(request);
        User user = userService.getVerifiedUser(JWTUtils.extractEmailFromJWT(jwt));

//        String rawFingerprint = getFingerprintFromCookie(request);

        return getSpringAuthToken(user);
    }

    private UsernamePasswordAuthenticationToken getSpringAuthToken(User user) {
        return getUsernamePasswordAuthenticationToken(new UserResponse(user));
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(UserResponse userResponse) {
        UserPrinciple principal = new UserPrinciple(userResponse);

        return new UsernamePasswordAuthenticationToken(
                userResponse.getEmail(),
                principal.getPassword(),
                principal.getAuthorities()
        );
    }

    private String getFingerprintFromCookie(HttpServletRequest request) throws FingerprintCookieNotFoundException {
        Cookie fingerprintCookie = Arrays.stream(request.getCookies())
                .filter(cookie -> FingerprintProperties.FINGERPRINT_COOKIE.equals(cookie.getName()))
                .findFirst().orElse(null);

        if (fingerprintCookie == null)
            throw new FingerprintCookieNotFoundException("Cookie does not contain fingerprint!");

        return fingerprintCookie.getValue();
    }


}
