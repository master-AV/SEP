package com.sep.api.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.function.Predicate;

@Configuration
public class RouteLocator {

//    public static final List<String> unprotectedURLs = List.of("/login");
//
//    public Predicate<ServerHttpRequest> isSecured = request -> unprotectedURLs.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
//
//
//    @Autowired
//    RequestFilter requestFilter;
//
    @Autowired
    AuthFilter authFilter;

    @Bean
    public org.springframework.cloud.gateway.route.RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        // adding 2 rotes to first microservice as we need to log request body if method is POST
        return builder.routes()
//                .route("first-microservice",r -> r.path("/first")
//                        .and().method("POST")
//                        .and().readBody(Student.class, s -> true).filters(f -> f.filters(requestFilter, authFilter))
//                        .uri("http://localhost:8081"))
                .route("PSP",r -> r.path("/cc/hi")
                        .and().method("GET").filters(f-> f.filters(authFilter))
//                        .uri("lb://PSP"))
                        .uri("http://localhost:8443/"))

//                        .uri("http://localhost:8081/bank/hi"))

                .build();
    }
}
