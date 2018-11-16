package com.tang.userserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @program: gitCode
 * @description: 测试
 * @author: tangjiwang
 * @Param $
 * @create: 2018-11-09 17:26
 **/
@RestController
public class TestController {

    @Value("${spring.redis.password}")
    String passwd;

    @PostMapping("/test")
    public String test() {
        return passwd;
    }



}
