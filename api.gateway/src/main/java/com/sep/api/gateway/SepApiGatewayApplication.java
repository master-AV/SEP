package com.sep.api.gateway;

import com.sep.api.gateway.config.EurekaServiceChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"ftn.sep.db"})
public class SepApiGatewayApplication {

	@Autowired
	private EurekaServiceChecker eurekaServiceChecker;
	public static void main(String[] args) {
		SpringApplication.run(SepApiGatewayApplication.class, args);
	}

}
