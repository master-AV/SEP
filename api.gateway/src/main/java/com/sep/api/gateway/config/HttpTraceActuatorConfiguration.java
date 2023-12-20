package com.sep.api.gateway.config;

import org.springframework.boot.actuate.web.exchanges.InMemoryHttpExchangeRepository;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// @Profile("actuator-endpoints")
// if you want: register bean only if profile is set
public class HttpTraceActuatorConfiguration {

    @Bean
    public InMemoryHttpExchangeRepository httpTraceRepository() {
        return new InMemoryHttpExchangeRepository();
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        RouteLocator r = builder.routes().build();
        System.out.println(r.getRoutes());
        return r;
    }

}