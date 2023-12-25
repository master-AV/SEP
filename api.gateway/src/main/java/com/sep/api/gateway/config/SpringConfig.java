//package com.sep.api.gateway.config;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.server.WebFilter;
//
//@Configuration
//public class SpringConfig {
//
//    @Autowired
//    RequestFilter requestFilter;
//
//    @Autowired
//    AuthFilter authFilter;
//
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        // adding 2 rotes to first microservice as we need to log request body if method is POST
//        return builder.routes()
//                .route("auth-server",r -> r.path("/login")
//                        .uri("http://localhost:8092"))
//                .build();
//    }
//
//    @Bean
//    public RestTemplate getRestTemplate() {
//        return new RestTemplate();
//    }
//
//    @Bean
//    public WebFilter responseFilter(){
//        return new PostGlobalFilter();
//    }
//
//}