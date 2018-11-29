package com.tang.msgserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.tang.msgserver.entity.ResponseModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 重置密码 发送的短信验证码
 * @author: tangjiwang
 * @Param $
 * @create: 2018-11-21 15:01
 **/
@RestController
public class ResetPasswdSmsController {
    @PostMapping(value = "/resetPasswd/sendSms", produces = "application/json;charset=UTF-8")
    public ResponseEntity<ResponseModel<JSONObject>> sendSms(@RequestBody JSONObject jsonObject) {


        return ResponseModel.success();
    }
}
