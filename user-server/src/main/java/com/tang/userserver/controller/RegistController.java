package com.tang.userserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.netflix.discovery.converters.Auto;
import com.tang.userserver.constant.ConstantProperties;
import com.tang.userserver.dao.ICommonDao;
import com.tang.userserver.dao.UserDao;
import com.tang.userserver.domain.User;
import com.tang.userserver.entity.ResponseModel;
import com.tang.userserver.exception.SPTException;
import com.tang.userserver.rpc.MsgServerRpc;
import com.tang.userserver.util.DateUtil;
import com.tang.userserver.util.JedisUtil;
import com.tang.userserver.util.StringUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
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

    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    private UserDao userDao;

    private static final String REGIST_PHONENUMBER_HASH = "REGIST_PHONENUMBER_HASH";
    private static final String VERIFIYCODE = "VERIFIYCODE_DATA:";
    private static final String REGIST_USERINFO_HASH = "REGIST_USERINFO_HASH:";

    @ApiOperation(value = "注册用户", notes = "tangjiwang")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "userName", value = "用户名", dataType = "string", required = true),
            @ApiImplicitParam(paramType = "query", name = "passwd", value = "输入第一次密码", dataType = "string", required = true),
            @ApiImplicitParam(paramType = "query", name = "passwdConfirm", value = "输入第二次密码", dataType = "string", required = true),
            @ApiImplicitParam(paramType = "query", name = "phoneNo", value = "手机号码", dataType = "string", required = true),
            @ApiImplicitParam(paramType = "query", name = "verifiyCode", value = "验证码", dataType = "string", required = true),
            @ApiImplicitParam(paramType = "query", name = "registChannel", value = "注册渠道 '0' : ios, '1' : anrioid , '2' : 'pc'", dataType = "string", required = true)
    })
    @PostMapping(value = "/toRegistUser", produces = "application/json;charset=UTF-8")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseModel<JSONObject>> toRegist(String userName, String passwd, String passwdConfirm
            , String phoneNo, String verifiyCode, String registChannel) {
        if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(passwd) || StringUtil.isEmpty(passwdConfirm)
                || StringUtil.isEmpty(phoneNo) || StringUtil.isEmpty(verifiyCode) || StringUtil.isEmpty(registChannel)) {
            return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultCode.ERROR_PARAM_ISNULL, ConstantProperties.ResultCode.ERROR_PARAM_ISNULL_VALUE);
        } else {
            if (!passwd.equals(passwdConfirm)) {
                return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_PASSWDCONFIRM, ConstantProperties.ResultRegist.ERROR_PASSWDDESC);
            } else {
                try {

                    String redisValue = jedisUtil.hget(REGIST_PHONENUMBER_HASH, phoneNo);
                    //看用户名是否存在。用户名必须是唯一的
                    String userInfo  = jedisUtil.hget(REGIST_USERINFO_HASH,userName);
                    if(StringUtil.isNotEmpty(userInfo)){
                        return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_USERNAME_EXISTS ,ConstantProperties.ResultRegist.ERROR_USERNAME_EXISTSDESC);
                    }
                    if (StringUtil.isEmpty(redisValue))//如果手机号码为空，说明该账户未注册过。允许注册
                    {

                        String verifiycdoe = jedisUtil.getRedisStrValue(VERIFIYCODE + phoneNo);//通过手机号码在缓存里找验证码。
                        if (StringUtil.isEmpty(verifiycdoe)){//如果验证码为空。说明验证码获取失败
                            return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_GET_VERIFIYCODE ,ConstantProperties.ResultRegist.ERROR_GET_VERIFIYCODEDESC);
                        } else {
                            if (verifiycdoe.equals(verifiyCode)) {
                                String registTime = DateUtil.longDate2StrDate(new Date().getTime());//注册时间
                                User user = new User();
                                user.setPhoneNo(phoneNo);
                                user.setPasswd(StringUtil.encryptStr(passwd));
                                user.setUserName(userName);
                                user.setVerifyCode(verifiyCode);
                                user.setRegistTime(registTime);
                                user.setRegistChannel(registChannel);
                                JSONObject jsonObject1 = new JSONObject();
                                jsonObject1.put("phoneNo", phoneNo);
                                jsonObject1.put("userName", userName);
                                jsonObject1.put("registChannel",registChannel);
                                jsonObject1.put("registTime",registTime);
                                int resultStatus = userDao.addUser(user);
                                if (1 == resultStatus) {
                                    jedisUtil.hset(REGIST_PHONENUMBER_HASH,phoneNo,jsonObject1.toJSONString());
                                    jedisUtil.hset(REGIST_USERINFO_HASH,userName,jsonObject1.toJSONString());
                                    return ResponseModel.success();
                                } else {
                                    return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_ADDUSER, ConstantProperties.ResultRegist.ERROR_ADDUSER_DESC);
                                }
                            } else {
                                return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_VERIFIYCODE, ConstantProperties.ResultRegist.ERROR_VERIFIYDESC);
                            }
                        }
                    } else {
                        return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_USEREXITS_CODE, ConstantProperties.ResultRegist.ERROR_USEREXITS_DESC);
                    }
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_ENCRYPT_CODE, ConstantProperties.ResultRegist.ERROR_ENCRYPT_DESC);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_ENCRYPT_CODE, ConstantProperties.ResultRegist.ERROR_ENCRYPT_DESC);
                }catch (SPTException e) {
                    e.printStackTrace();
                    return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultCode.ERROR_OTHER, ConstantProperties.ResultCode.ERROR_OTHER_DESC);
                }
            }
        }
    }


    @ApiOperation(value = "注册时发送的短信验证码", notes = "tangjiwang")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phoneno", value = "手机号码", dataType = "string", required = true)
    })
    @PostMapping(value = "/toSendVrifiyCode", produces = "application/json;charset=UTF-8")
    public ResponseEntity<ResponseModel<JSONObject>> toSendVerifyCode(String phoneno) {
        JSONObject jsonObject = new JSONObject();
        String phoneNo = phoneno;
        String sources = "0123456789"; // 加上一些字母，就可以生成pc站的验证码了
        Random rand = new Random();
        StringBuffer verifiyCode = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            verifiyCode.append(sources.charAt(rand.nextInt(10)) + "");
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
                return ResponseModel.error(HttpStatus.OK, responseModel.getRespCode(), responseModel.getRespDesc());
            }
        }
        return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultCode.ERROR_OTHER, ConstantProperties.ResultCode.ERROR_OTHER_DESC);
    }

}
