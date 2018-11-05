package com.tang.appuser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AppuserApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppuserApplication.class, args);
    }
}
