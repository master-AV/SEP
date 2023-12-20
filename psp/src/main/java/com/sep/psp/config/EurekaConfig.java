package com.sep.psp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EurekaConfig {
    @Value("${eureka.instance.hostname}")
    private String hostname;

    // Set the hostname
    public EurekaConfig() {
        System.setProperty("eureka.instance.hostname", "localhost");
    }
}
