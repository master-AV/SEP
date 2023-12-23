package com.sep.api.gateway.smart.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Date;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.sep.api.gateway.smart.utils.JwtProperties.TOKEN_PREFIX;

public class JWTUtils {
    public static String generateJWT(String email, String rawFingerPrint) {
        return JWT.create()
                .withSubject(email)
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim(FingerprintProperties.FINGERPRINT_CLAIM, FingerprintUtils.generateFingerprint(rawFingerPrint))
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));
    }

    public static String extractTokenFromRequest(ServerHttpRequest request) {
        return request.getHeaders().getFirst(JwtProperties.HEADER_STRING).replace(TOKEN_PREFIX, "");
    }

    public static DecodedJWT decodeToken(String token) {
        return JWT.require(HMAC512(JwtProperties.SECRET.getBytes())).build().verify(token);
    }

    public static DecodedJWT extractJWTFromRequest(ServerHttpRequest request) {
        return decodeToken(extractTokenFromRequest(request));
    }

    public static String extractTokenFromJWT(DecodedJWT jwt) {
        return jwt.getToken();
    }

    public static String extractEmailFromJWT(DecodedJWT jwt)  {
        String email = jwt.getSubject();
        if (email == null)
            throw new RuntimeException("Cannot extract email/subject from jwt!");
        return email;
    }

    public static String extractFingerprintFromJWT(DecodedJWT jwt) {
        return jwt.getClaim(FingerprintProperties.FINGERPRINT_CLAIM).asString();
    }

    public static boolean jwtHasExpired(String token) {
        return decodeToken(token).getExpiresAt().before(new Date());
    }
}
