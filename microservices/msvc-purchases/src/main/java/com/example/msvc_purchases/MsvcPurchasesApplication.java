package com.example.msvc_purchases;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.msvc_purchases.client")
public class MsvcPurchasesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcPurchasesApplication.class, args);
	}

}
