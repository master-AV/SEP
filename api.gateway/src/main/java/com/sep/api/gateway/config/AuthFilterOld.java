/*
package com.sep.api.gateway.config;

import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.sep.api.gateway.smart.utils.JwtProperties.*;

@Component
@RefreshScope
public class AuthFilter implements GatewayFilter {

    @Autowired
    RouteLocator routeValidator;

    @Value("${authentication.enabled}")
    private boolean authEnabled;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(!authEnabled) {
            System.out.println("Authentication is disabled. To enable it, make \"authentication.enabled\" property as true");
            return chain.filter(exchange);
        }
        String token ="";
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse res = exchange.getResponse();

        request.getHeaders();
        if (request.getHeaders().containsKey(HEADER_STRING)){
            String header = request.getHeaders().get(HEADER_STRING).toString();
            if (headerIsInvalid(header)) {
//                chain.doFilter(request, response);
                return chain.filter(exchange);
            }
            else {
                Authentication authentication = getAuthentication(request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return chain.filter(exchange);
            }
        }
        */
/*if(routeValidator.isSecured.test(request)) {
            System.out.println("validating authentication token");
            if(this.isCredsMissing(request)) {
                System.out.println("in error");
                return this.onError(exchange,"Credentials missing",HttpStatus.UNAUTHORIZED);
            }
            if (request.getHeaders().containsKey("userName") && request.getHeaders().containsKey("role")) {
                token = authUtil.getToken(request.getHeaders().get("userName").toString(), request.getHeaders().get("role").toString());
            }
            else {
                token = request.getHeaders().get("Authorization").toString().split(" ")[1];
            }

            if(jwtUtil.isInvalid(token)) {
                return this.onError(exchange,"Auth header invalid",HttpStatus.UNAUTHORIZED);
            }
            else {
                System.out.println("Authentication is successful");
            }

            this.populateRequestWithHeaders(exchange,token);
        }*//*

        return chain.filter(exchange);
    }

    private boolean headerIsInvalid(String header){

        return header == null || !header.startsWith(TOKEN_PREFIX);
    }

    private Authentication getAuthentication(ServerHttpRequest request) {
        Authentication authentication = null;
        try {
            authentication = getUsernamePasswordAuthentication(request);
        } catch (Exception e) {// EntityNotFoundException
            e.printStackTrace();
        }
        return authentication;
    }
    private Authentication getUsernamePasswordAuthentication(ServerHttpRequest request) {
        String token = request.getHeaders().get(HEADER_STRING).toString().replace(TOKEN_PREFIX,"");
//                request.getHeader(JwtProperties.HEADER_STRING)


        String email = JWT.require(HMAC512(SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();

        return email != null ? getSpringAuthToken(email) : null;
    }


//    private UsernamePasswordAuthenticationToken getSpringAuthToken(String email)
//    {
//        UserResponse userDTO = new UserResponse(userService.getVerifiedUser(email));
//        return getUsernamePasswordAuthenticationToken(userDTO);
//    }
*/
/*
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return  request.getHeaders().getOrEmpty("Authorization").get(0);
    }


    private boolean isCredsMissing(ServerHttpRequest request) {
        return !(request.getHeaders().containsKey("userName") && request.getHeaders().containsKey("role")) && !request.getHeaders().containsKey("Authorization");
    }

    private void populateRequestWithHeaders(ServerWebExchange exchange, String token) {
        Claims claims = jwtUtil.getALlClaims(token);
        exchange.getRequest()
                .mutate()
                .header("id",String.valueOf(claims.get("id")))
                .header("role", String.valueOf(claims.get("role")))
                .build();
    }*//*

}
*/
