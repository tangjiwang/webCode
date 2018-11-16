package com.tang.msgserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.tang.msgserver.constant.ConstantProperties;
import com.tang.msgserver.entity.ResponseModel;
import com.tang.msgserver.exception.SPTException;
import com.tang.msgserver.util.JedisUtil;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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


    @PostMapping(value = "/sendSms",produces = "application/json;charset=UTF-8")
    public ResponseEntity<ResponseModel<JSONObject>> sendSms(@RequestBody JSONObject jsonObject) {
        if(null != jsonObject)
        {
            try {
                String phoneno =jsonObject.getString("phoneNo");//收件人手机号码
                String verifiycode = jsonObject.getString("verifiyCode");//短信验证码

                String verifiyValue =jedisUtil.getRedisStrValue("verifiycode:" + phoneno);//获取缓存是否有数据
                if(null != verifiyValue)//防止恶意短信炮轰。 2min内只允许发送一次
                {
                    return ResponseModel.error(HttpStatus.OK, ConstantProperties.SendSMS.ERROR_SENDTIME, "2分钟内只允许发送一次验证码!");
                }else
                {
                    HttpClient client = new HttpClient();
                    PostMethod post = new PostMethod("http://utf8.api.smschinese.cn");
                    post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
                    NameValuePair[] data = {new NameValuePair("Uid", "tjwang")
                            , new NameValuePair("Key", "d41d8cd98f00b204e980")
                            , new NameValuePair("smsMob", phoneno)
                            , new NameValuePair("smsText", "验证码：" + verifiycode)};

                    if (null != data && data.length > 0) {
//                        String regEx = "[^0-9]";
//                        Pattern p = Pattern.compile(regEx);
//                        Matcher m = p.matcher(verifiycode);
//                        String verifyCode = m.replaceAll("").trim();
                        post.setRequestBody(data);
                        client.executeMethod(post);
                        //Header[] headers = post.getResponseHeaders();
                        int statusCode = post.getStatusCode();//发送结果状态 200成功
                        if (SEND_SUCCESS == statusCode) {
                            jedisUtil.setRedisStrValue("verifiycode:" + phoneno, verifiycode, 60 * 2);
                        }
                        post.releaseConnection();
                    }
                }
            } catch (HttpException e) {
                e.printStackTrace();
                return ResponseModel.error(HttpStatus.NO_CONTENT, ConstantProperties.WangJianProps.CONNECT_EXCEPTION, "连接中国网建异常!");
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseModel.error(HttpStatus.NO_CONTENT, ConstantProperties.WangJianProps.CONNECT_EXCEPTION, "连接中国网建异常!");
            } catch (SPTException e) {
                e.printStackTrace();
                return ResponseModel.error(HttpStatus.NO_CONTENT, ConstantProperties.RedisProps.CONNECT_EXCEPTION, "redis 连接异常!");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseModel.error(HttpStatus.NO_CONTENT, ConstantProperties.ResultCode.ERROR_OTHER, "系统异常,联系系统管理员!");
            }
            return ResponseModel.success();
        }
        else
        {
            return ResponseModel.error(HttpStatus.OK,ConstantProperties.ResultCode.ERROR_PARAM,"参数为空");
        }


    }
}
