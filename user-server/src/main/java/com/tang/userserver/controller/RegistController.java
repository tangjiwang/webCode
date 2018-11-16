package com.tang.userserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.tang.userserver.constant.ConstantProperties;
import com.tang.userserver.entity.ResponseModel;
import com.tang.userserver.rpc.MsgServerRpc;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Random;


/**
 * @program: gitCode
 * @description: 注册用户
 * @author: tangjiwang
 * @Param $
 * @create: 2018-11-15 14:53
 **/
@RestController
@RequestMapping("/regist")
@Api(tags = "用户注册中心")
public class RegistController {

    @Autowired
    private MsgServerRpc msgServerRpc;

    @ApiOperation(value = "发送短信验证码", notes = "tangjiwang")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phoneno", value = "手机号码", dataType = "string", required = true)
    })
    @PostMapping(value = "/toSendVrifiyCode",produces = "application/json;charset=UTF-8")
    public ResponseEntity<ResponseModel<JSONObject>> toSendVerifyCode(String phoneno) {
        JSONObject jsonObject = new JSONObject();
        String phoneNo = phoneno;
        String sources = "0123456789"; // 加上一些字母，就可以生成pc站的验证码了
        Random rand = new Random();
        StringBuffer verifiyCode = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            verifiyCode.append(sources.charAt(rand.nextInt(9)) + "");
        }
        if (verifiyCode.equals(""))//如果6位数随机数 为空，给个默认验证码
        {
            verifiyCode.append(ConstantProperties.SendSMS.VERIFIYCODE);
        }
        jsonObject.put("phoneNo", phoneNo);
        jsonObject.put("verifiyCode", verifiyCode.toString());
        ResponseModel<JSONObject> responseModel = msgServerRpc.sendSms(jsonObject);
        if (null != responseModel) {
            if (responseModel.getRespCode() == ConstantProperties.ResultCode.RESP_SUCCESS) {
               return ResponseModel.success();
            } else {
                return ResponseModel.error(HttpStatus.OK,responseModel.getRespCode(),responseModel.getRespDesc());
            }
        }
        return ResponseModel.error(HttpStatus.INTERNAL_SERVER_ERROR, ConstantProperties.ResultCode.ERROR_OTHER,ConstantProperties.ResultCode.ERROR_OTHER_DESC);

    }

}
