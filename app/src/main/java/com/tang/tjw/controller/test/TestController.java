package com.tang.tjw.controller.test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: tjw
 * @description: 测试controller
 * @author: tangjiwang
 * @Param $
 * @create: 2018-11-05 14:08
 **/
@RestController
public class TestController {

@Value("${spring.redis.host}")
String demo ;

@Value("${spring.redis.port}")
String title;
    @PostMapping(value = "/do")
    public String test()
    {
        System.out.println(title);
        System.out.println("11");
        return  demo;
    }
}
