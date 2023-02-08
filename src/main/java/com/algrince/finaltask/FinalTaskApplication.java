package com.algrince.finaltask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.algrince.finaltask")
@EnableAutoConfiguration
public class FinalTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalTaskApplication.class, args);
	}

}
