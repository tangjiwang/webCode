package com.tang.userserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


/**
 * @program: gitCode
 * @description: 主类
 * @author: tangjiwang
 * @Param $
 * @create: 2018-11-10 11:07
 **/
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UserServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServerApplication.class, args);
    }

}
