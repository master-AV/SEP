//package com.sep.api.gateway.config;
//
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.sep.api.gateway.service.UserResponse;
//import com.sep.api.gateway.service.UserService;
//import com.sep.api.gateway.smart.utils.FingerprintProperties;
//import com.sep.api.gateway.smart.utils.FingerprintUtils;
//import com.sep.api.gateway.smart.utils.JWTUtils;
//import com.sep.api.gateway.smart.utils.UserPrinciple;
//import ftn.sep.db.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.http.HttpCookie;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//import java.util.Map;
//
//import static com.sep.api.gateway.smart.utils.JwtProperties.HEADER_STRING;
//import static com.sep.api.gateway.smart.utils.JwtProperties.TOKEN_PREFIX;
//
//@Component
//@RefreshScope
//public class AuthFilter implements GatewayFilter {
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    RouteValidator routeValidator;
//
////    public JwtAuthenticationFilter(UserService userService) {
////        this.userService = userService;
////    }
//
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//        ServerHttpRequest request = exchange.getRequest();
//        if (routeValidator.isSecured.test(request)) {
//            if (!headerIsInvalid(request.getHeaders().get(HEADER_STRING).get(0))) {
//                Authentication authentication = getAuthentication(request);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
//
//        return chain.filter(exchange);
//    }
//
//    private boolean headerIsInvalid(String header) {
//        return header == null || !header.startsWith(TOKEN_PREFIX) || header.equals(TOKEN_PREFIX);
//    }
//
//    private Authentication getAuthentication(ServerHttpRequest request) {
//        try {
//            return getUsernamePasswordAuthentication(request);
//        } catch (Exception ignored) {
//            return null;
//        }
//    }
//
//    private Authentication getUsernamePasswordAuthentication(ServerHttpRequest request) {
//        DecodedJWT jwt = JWTUtils.extractJWTFromRequest(request);
//        User user = userService.getVerifiedUser(JWTUtils.extractEmailFromJWT(jwt));
//
////        if (isJwtBlacklisted(user, jwt.getToken()))
////            return null;
//
//        String rawFingerprint = getFingerprintFromCookie(request);
//        if (!FingerprintUtils.verifyFingerprint(JWTUtils.extractFingerprintFromJWT(jwt), rawFingerprint)) {
////            userService.updateUsersJWTBlacklist(user, new BlacklistedJWT(JWTUtils.extractTokenFromJWT(jwt), user));
//            return null;
//        }
//
//        return getSpringAuthToken(user);
//    }
//
//    private UsernamePasswordAuthenticationToken getSpringAuthToken(User user) {
//        return getUsernamePasswordAuthenticationToken(new UserResponse(user));
//    }
//
//    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(UserResponse userResponse) {
//        UserPrinciple principal = new UserPrinciple(userResponse);
//
//        return new UsernamePasswordAuthenticationToken(
//                userResponse.getEmail(),
//                principal.getPassword(),
//                principal.getAuthorities()
//        );
//    }
//
//    private String getFingerprintFromCookie(ServerHttpRequest request) {
//        HttpCookie fingerprintCookie = null;
//        for (Map.Entry<String, List<HttpCookie>> entry : request.getCookies().entrySet()){
//            HttpCookie cookie = entry.getValue().get(0);
//            if (FingerprintProperties.FINGERPRINT_COOKIE.equals(cookie))
//                fingerprintCookie = cookie;
//        }
////        for (MultiValueMap<String, HttpCookie> cook : request.getCookies()){
////        }
////        Cookie fingerprintCookie = request.getCookies().entrySet().stream()
////                .filter(cookieValueKeyPair ->
////                        FingerprintProperties.FINGERPRINT_COOKIE.equals(cookieValueKeyPair.getValue()))
////                .findFirst().orElse(null);
//
//        if (fingerprintCookie == null)
//            throw new RuntimeException("Cookie does not contain fingerprint!");
//
//        return fingerprintCookie.getValue();
//    }
//
////    private boolean isJwtBlacklisted(User user, String jwt) {
////        for (BlacklistedJWT blJWT : user.getBlacklistedJWTs())
////            if (blJWT.getJwt().equals(jwt))
////                return true;
////        return false;
////    }
//
//}
