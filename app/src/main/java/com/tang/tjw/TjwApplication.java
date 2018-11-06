package com.tang.tjw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TjwApplication {

	public static void main(String[] args) {
		SpringApplication.run(TjwApplication.class, args);
	}
}
