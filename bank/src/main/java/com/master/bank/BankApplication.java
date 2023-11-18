package com.master.bank;

import com.google.common.hash.Hashing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class BankApplication {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
//		String sha256hex = Hashing.sha256()
//			.hashString("123", StandardCharsets.UTF_8)
//			.toString();
//		System.out.println(sha256hex);
		SpringApplication.run(BankApplication.class, args);
	}

}
