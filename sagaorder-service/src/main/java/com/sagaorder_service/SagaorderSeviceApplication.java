package com.sagaorder_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SagaorderSeviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SagaorderSeviceApplication.class, args);
	}

}
