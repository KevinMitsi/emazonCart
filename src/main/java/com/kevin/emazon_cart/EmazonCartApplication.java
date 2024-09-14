package com.kevin.emazon_cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmazonCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmazonCartApplication.class, args);
	}

}
