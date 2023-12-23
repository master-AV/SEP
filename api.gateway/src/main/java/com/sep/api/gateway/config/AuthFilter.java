package com.sep.api.gateway.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.sep.api.gateway.smart.utils.FingerprintProperties;
import com.sep.api.gateway.smart.utils.FingerprintUtils;
import com.sep.api.gateway.smart.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static com.sep.api.gateway.smart.utils.JwtProperties.HEADER_STRING;
import static com.sep.api.gateway.smart.utils.JwtProperties.TOKEN_PREFIX;

@Component
@RefreshScope
public class AuthFilter implements GatewayFilter {

    @Autowired
    RouteValidator routeValidator;


    @Value("${authentication.enabled}")
    private boolean authEnabled;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        if(!authEnabled) {
//            System.out.println("Authentication is disabled. To enable it, make \"authentication.enabled\" property as true");
//            return chain.filter(exchange);
//        }
        String token ="";
        ServerHttpRequest request = exchange.getRequest();

        if(routeValidator.isSecured.test(request)) {
            System.out.println("validating authentication token");
            checkCookies(request);
            if (headerIsInvalid(request.getHeaders().get(HEADER_STRING).get(0))) {
                throw new RuntimeException("Auth not successfull");
            }
        }
        return chain.filter(exchange);
    }

    private void checkCookies(ServerHttpRequest request) {
        DecodedJWT jwt = JWTUtils.extractJWTFromRequest(request);
//        User user = userService.getVerifiedUser(JWTUtils.extractEmailFromJWT(jwt));

//        if (isJwtBlacklisted(user, jwt.getToken()))
//            return null;

        String rawFingerprint = getFingerprintFromCookie(request);
        if (!FingerprintUtils.verifyFingerprint(JWTUtils.extractFingerprintFromJWT(jwt), rawFingerprint)) {
//            userService.updateUsersJWTBlacklist(user, new BlacklistedJWT(JWTUtils.extractTokenFromJWT(jwt), user));
            throw new RuntimeException("Cookie is rotten");
        }

    }
    private String getFingerprintFromCookie(ServerHttpRequest request) {
        HttpCookie fingerprintCookie = null;
        for (Map.Entry<String, List<HttpCookie>> entry : request.getCookies().entrySet()){
//            HttpCookie cookie = entry.getValue().get(0);
            if (FingerprintProperties.FINGERPRINT_COOKIE.equals(entry.getKey()))
                fingerprintCookie = entry.getValue().get(0);
        }
//        for (MultiValueMap<String, HttpCookie> cook : request.getCookies()){
//        }
//        Cookie fingerprintCookie = request.getCookies().entrySet().stream()
//                .filter(cookieValueKeyPair ->
//                        FingerprintProperties.FINGERPRINT_COOKIE.equals(cookieValueKeyPair.getValue()))
//                .findFirst().orElse(null);

        if (fingerprintCookie == null)
            throw new RuntimeException("Cookie does not contain fingerprint!");

        return fingerprintCookie.getValue();
    }

    private boolean headerIsInvalid(String header) {
        return header == null || !header.startsWith(TOKEN_PREFIX) || header.equals(TOKEN_PREFIX);
    }

//    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
//        ServerHttpResponse response = exchange.getResponse();
//        response.setStatusCode(httpStatus);
//        return response.setComplete();
//    }

//    private String getAuthHeader(ServerHttpRequest request) {
//        return  request.getHeaders().getOrEmpty("Authorization").get(0);
//    }


//    private boolean isCredsMissing(ServerHttpRequest request) {
//        return !(request.getHeaders().containsKey("userName") && request.getHeaders().containsKey("role")) && !request.getHeaders().containsKey("Authorization");
//    }
//
//    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
//        Claims claims = jwtUtil.getALlClaims(token);
//        exchange.getRequest()
//                .mutate()
//                .header("id",String.valueOf(claims.get("id")))
//                .header("role", String.valueOf(claims.get("role")))
//                .build();
//    }
}