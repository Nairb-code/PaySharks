package com.bripay.oauthservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

//@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class OauthServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(OauthServiceApplication.class, args);
	}

}
