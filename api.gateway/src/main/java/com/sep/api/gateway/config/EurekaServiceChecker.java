package com.sep.api.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
public class EurekaServiceChecker {

    @Autowired
    private DiscoveryClient discoveryClient;


    @Bean
    public void printAvailableServices() {
        List<String> services = discoveryClient.getServices();
        System.out.println("Services registered with Eureka:");
        for (String service : services) {
            System.out.println(service);
        }
    }
}
