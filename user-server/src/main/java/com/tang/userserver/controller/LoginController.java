package com.tang.userserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.tang.userserver.constant.ConstantProperties;
import com.tang.userserver.dao.UserDao;
import com.tang.userserver.domain.User;
import com.tang.userserver.entity.ResponseModel;
import com.tang.userserver.exception.SPTException;
import com.tang.userserver.util.DateUtil;
import com.tang.userserver.util.JedisUtil;
import com.tang.userserver.util.StringUtil;
import io.swagger.annotations.*;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import javax.xml.transform.Result;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @program: gitCode
 * @description: 登陆controller
 * @author: tangjiwang
 * @Param $
 * @create: 2018-11-16 23:08
 **/
@RestController
@RequestMapping("/toLogin")
@Api(tags = "用户登录中心")
public class LoginController {
    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    private UserDao userDao;

    private static final String REGIST_PHONENUMBER_HASH = "REGIST_PHONENUMBER_HASH";
    private static final String VERIFIYCODE = "VERIFIYCODE_DATA:";
    private static final String REGIST_USERINFO_HASH = "REGIST_USERINFO_HASH:";

    private static final String LOGIN_USERSESSIONINFO = "LOGIN_USERSESSIONINFO_HASH";

    @ApiOperation(value = "通过用户名密码登陆", notes = "tangjiwnag")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", dataType = "string", required = true),
            @ApiImplicitParam(paramType = "query", name = "passwd", dataType = "string", required = true),
            @ApiImplicitParam(paramType = "query", name = "channel", dataType = "string", required = true)

    })
    @PostMapping(value = "/toLogin_userNmPasswd", produces = "application/json;charset=utf-8")
    @Transactional(rollbackFor = SPTException.class)
    public ResponseEntity<ResponseModel<JSObject>> toLoginFromUserPasswd(HttpSession session, String username, String passwd, String channel) {
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(passwd) || StringUtil.isEmpty(channel)) {
            return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultCode.ERROR_PARAM_ISNULL, ConstantProperties.ResultCode.ERROR_PARAM_ISNULL_VALUE);
        } else {
            //通过用户名在数据库里找密码
            try {
                String value = jedisUtil.hget(REGIST_USERINFO_HASH, username);//在注册信息缓存里通过用户名获取信息


                if (StringUtil.isEmpty(value)){//如果缓存里没有数据。说明未注册
                    return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_USER_NOTREGIST, ConstantProperties.ResultRegist.ERROR_USER_NOTREGISTDESC);
                }

                List<Map<String, Object>> list = userDao.queryPasswdByName(username);   //从数据库通过用户名获取密码

                JSONObject jsonObject1 = JSONObject.parseObject(value);
                if (null != jsonObject1) {

                    String loginTime = DateUtil.longDate2StrDate(new Date().getTime());//登陆时间
                    String phoneNo = jsonObject1.getString("phoneNo");
                    String encryPasswd = "";
                    if (null != list && list.size() > 0) {
                        encryPasswd = list.get(0).get("passwd") == null ? "" : list.get(0).get("passwd").toString();//数据库里获取的密文密码
                    }

                    if (StringUtil.encryptStr(passwd).equals(encryPasswd)) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("phoneNo", phoneNo);
                        jsonObject.put("username", username);
                        jsonObject.put("channel", channel);
                        jsonObject.put("logintime", loginTime);
                        session.setAttribute("user", jsonObject);
                        jedisUtil.hset(LOGIN_USERSESSIONINFO, phoneNo, jsonObject.toJSONString());
                    } else {
                        return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_USER_NAMEPASSWD, ConstantProperties.ResultRegist.ERROR_USER_NAMEPASSWDDESC);
                    }
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_ENCRYPT_CODE, ConstantProperties.ResultRegist.ERROR_ENCRYPT_DESC);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_ENCRYPT_CODE, ConstantProperties.ResultRegist.ERROR_ENCRYPT_DESC);
            } catch (SPTException e) {
                e.printStackTrace();
                return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultCode.ERROR_OTHER, ConstantProperties.ResultCode.ERROR_OTHER_DESC);
            }

        }
        return ResponseModel.success();
    }

    @ApiOperation(value = "通过手机号码+短信验证码登陆", notes = "tangjiwnag")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "phoneNo", dataType = "string", required = true),
            @ApiImplicitParam(paramType = "query", name = "verifiyCode", dataType = "string", required = true),
            @ApiImplicitParam(paramType = "query", name = "channel", dataType = "string", required = true)
    })
    @PostMapping(value = "/toLogin_phoVerify", produces = "application/json;charset=utf-8")
    public ResponseEntity<ResponseModel<JSObject>> toLoginFromPhone(HttpSession session, String phoneNo, String verifiyCode, String channel) {
        if (StringUtil.isEmpty(phoneNo) || StringUtil.isEmpty(verifiyCode) || StringUtil.isEmpty(channel)) {
            ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultCode.ERROR_PARAM_ISNULL, ConstantProperties.ResultCode.ERROR_PARAM_ISNULL_VALUE);
        }
        try {
            String redis_phone = jedisUtil.hget(REGIST_PHONENUMBER_HASH, phoneNo);//缓存中查找手机号码

            String verifiy = jedisUtil.getRedisStrValue(VERIFIYCODE + phoneNo);      //通过手机号码获取验证码

            if (StringUtil.isEmpty(verifiy)) {//获取验证码失败
                return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_GET_VERIFIYCODE, ConstantProperties.ResultRegist.ERROR_GET_VERIFIYCODEDESC);
            } else {
                if (!StringUtil.isEmpty(redis_phone)) {
                    if (!verifiy.equals(verifiyCode)) {
                        return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_VERIFIYCODE, ConstantProperties.ResultRegist.ERROR_VERIFIYDESC);
                    } else {
                        JSONObject jsonObject = JSONObject.parseObject(redis_phone);
                        String loginTime = DateUtil.longDate2StrDate(new Date().getTime());//当前时间
                        JSONObject jsonObject1 = new JSONObject();

                        jsonObject1.put("phoneNo", phoneNo);
                        jsonObject1.put("username", jsonObject.getString("userName"));
                        jsonObject1.put("loginchannel", channel);
                        jsonObject1.put("logintime", loginTime);
                        session.setAttribute("user", jsonObject1);
                        jedisUtil.hset(LOGIN_USERSESSIONINFO, phoneNo, jsonObject1.toJSONString());
                        jedisUtil.delRedisStrValue(VERIFIYCODE + phoneNo);
                    }
                } else {
                    //该手机号没有注册，默认给注册。用户名为随机 WW_开头随机数。 密码 根据用户名加密。 后续自己修改。
                    StringBuilder sb = new StringBuilder("WW_");
                    //前6位YYYYMMDDD
                    //后6位随机数
                    String time = DateUtil.longTimeToYMD(new Date().getTime());
                    sb.append(time);
                    Random random = new Random();
                    for (int i = 0; i < 6; i++) {
                        sb.append(random.nextInt(10));
                    }
                    String registTime = DateUtil.longDate2StrDate(new Date().getTime());//注册时间
                    String passwd = StringUtil.encryptStr(sb.toString());//加密后的密码
                    User user = new User();
                    user.setUserName(sb.toString());
                    user.setPasswd(passwd);
                    user.setPhoneNo(phoneNo);
                    user.setRegistTime(registTime);
                    user.setRegistChannel(channel);
                    int resultStatus = userDao.addUser(user);

                    if (1 == resultStatus) {//把登陆数据放到缓存里
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("phoneNo", phoneNo);
                        jsonObject1.put("username", sb.toString());
                        jsonObject1.put("channel", channel);
                        jsonObject1.put("loginTime", registTime);
                        session.setAttribute("user", jsonObject1);
                        jedisUtil.hset(LOGIN_USERSESSIONINFO, phoneNo, jsonObject1.toJSONString());
                        jedisUtil.delRedisStrValue(VERIFIYCODE + phoneNo);
                    } else {
                        return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_ADDUSER, ConstantProperties.ResultRegist.ERROR_ADDUSER_DESC);
                    }
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_ENCRYPT_CODE, ConstantProperties.ResultRegist.ERROR_ENCRYPT_DESC);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_ENCRYPT_CODE, ConstantProperties.ResultRegist.ERROR_ENCRYPT_DESC);
        } catch (SPTException e) {
            e.printStackTrace();
            return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultCode.ERROR_OTHER, ConstantProperties.ResultCode.ERROR_OTHER_DESC);
        }
        return ResponseModel.success();
    }
}
