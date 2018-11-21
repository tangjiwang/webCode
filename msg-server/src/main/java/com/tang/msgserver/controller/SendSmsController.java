package com.tang.msgserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.tang.msgserver.constant.ConstantProperties;
import com.tang.msgserver.entity.ResponseModel;
import com.tang.msgserver.exception.SPTException;
import com.tang.msgserver.util.JedisUtil;
import com.tang.msgserver.util.SendSmsUtil;
import com.tang.msgserver.util.StringUtil;
import org.apache.commons.httpclient.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * @program: gitCode
 * @description:
 * @author: tangjiwang
 * @Param $
 * @create: 2018-11-15 11:27
 **/
@RestController
public class SendSmsController {

    private static final int SEND_SUCCESS = 200;

    @Autowired
    private JedisUtil jedisUtil;

    private static final String VERIFIYCODE = "VERIFIYCODE_DATA:";
    private static final String REGIST_PHONENUMBER_HASH = "REGIST_PHONENUMBER_HASH";


    @PostMapping(value = "/regist/sendSms", produces = "application/json;charset=UTF-8")
    public ResponseEntity<ResponseModel<JSONObject>> sendSms(@RequestBody JSONObject jsonObject) {
        if (null != jsonObject) {
            try {
                String phoneno = jsonObject.getString("phoneNo");//收件人手机号码
                String verifiycode = jsonObject.getString("verifiyCode");//短信验证码
                String registPhone = jedisUtil.hget(REGIST_PHONENUMBER_HASH, phoneno);//获取该手机号是否已经注册过
                if (StringUtil.isEmpty(registPhone)) {
                    String verifiyValue = jedisUtil.getRedisStrValue(VERIFIYCODE + phoneno);//获取缓存是否有数据
                    if (null != verifiyValue)//防止恶意短信炮轰。 2min内只允许发送一次
                    {
                        return ResponseModel.error(HttpStatus.OK, ConstantProperties.SendSMS.ERROR_SENDTIME, ConstantProperties.SendSMS.SMSERROR_TIPS);
                    } else {
                        int statusCode = SendSmsUtil.sendSms(phoneno, verifiycode);//发送返回状态
                        if (SEND_SUCCESS == statusCode) {//把验证码放入缓存里
                            jedisUtil.setRedisStrValue(VERIFIYCODE + phoneno, verifiycode, ConstantProperties.SendSMS.SMSTIME);
                        }
                    }
                } else {
                    return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_USEREXITS_CODE, ConstantProperties.ResultRegist.ERROR_USEREXITS_DESC);
                }
            } catch (HttpException e) {
                e.printStackTrace();
                return ResponseModel.error(HttpStatus.OK, ConstantProperties.WangJianProps.CONNECT_EXCEPTION, ConstantProperties.WangJianProps.CONNECT_EXCEPTIONVALUE);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseModel.error(HttpStatus.NO_CONTENT, ConstantProperties.WangJianProps.CONNECT_EXCEPTION, ConstantProperties.WangJianProps.CONNECT_EXCEPTIONVALUE);
            } catch (SPTException e) {
                e.printStackTrace();
                return ResponseModel.error(HttpStatus.NO_CONTENT, ConstantProperties.RedisProps.CONNECT_EXCEPTION, ConstantProperties.RedisProps.CONNECT_REDIS_VALUE);
            }
            return ResponseModel.success();
        } else {
            return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultCode.ERROR_PARAM, ConstantProperties.ResultCode.ERROR_PARAM_ISNULL_VALUE);
        }
    }


}
