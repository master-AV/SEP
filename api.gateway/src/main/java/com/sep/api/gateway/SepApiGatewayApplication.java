package com.sep.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"ftn.sep.db"})
public class SepApiGatewayApplication {
	public static void main(String[] args) {
		SpringApplication.run(SepApiGatewayApplication.class, args);
	}
}
