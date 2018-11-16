package com.tang.msgserver.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;

/**
 * @program: gitCode
 * @description: 通过中国网建系统发送短信
 * @author: tangjiwang
 * @Param $手机号码，验证码
 * @create: 2018-11-16 14:28
 **/
public class SendSmsUtil {

    public static int sendSms(String phoneNo, String verifiyCode) throws IOException {
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://utf8.api.smschinese.cn");
        post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
        NameValuePair[] data = {new NameValuePair("Uid", "tjwang")
                , new NameValuePair("Key", "d41d8cd98f00b204e980")
                , new NameValuePair("smsMob", phoneNo)
                , new NameValuePair("smsText", "验证码：" + verifiyCode)};

//                        String regEx = "[^0-9]";
//                        Pattern p = Pattern.compile(regEx);
//                        Matcher m = p.matcher(verifiycode);
//                        String verifyCode = m.replaceAll("").trim();
        post.setRequestBody(data);
        client.executeMethod(post);
        //Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();//发送结果状态 200成功
        post.releaseConnection();
        return statusCode;
    }
}
