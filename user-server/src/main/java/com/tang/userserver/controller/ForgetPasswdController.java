package com.tang.userserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.tang.userserver.constant.ConstantProperties;
import com.tang.userserver.dao.UserDao;
import com.tang.userserver.entity.ResponseModel;
import com.tang.userserver.exception.SPTException;
import com.tang.userserver.util.JedisUtil;
import com.tang.userserver.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: gitCode
 * @description: 忘记密码
 * @author: tangjiwang
 * @Param $
 * @create: 2018-11-21 14:20
 **/
@RestController
@RequestMapping("/forgetPasswd")
@Api(tags = "忘记密码,通过手机号码重新密码")
public class ForgetPasswdController {

    private static final String REGIST_PHONENUMBER_HASH = "REGIST_PHONENUMBER_HASH:";
    private static final String VERIFIYCODE = "VERIFIYCODE_DATA:";
    private static final String REGIST_USERINFO_HASH = "REGIST_USERINFO_HASH:";
    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    private UserDao userDao;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "username", dataType = "string", required = true),
            @ApiImplicitParam(paramType = "query", name = "phoneno", dataType = "string", required = true),
            @ApiImplicitParam(paramType = "query", name = "verifiycode", dataType = "string", required = true)
    })
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/toResetPasswd", produces = "application/json;charset=utf-8")
    public ResponseEntity<ResponseModel<JSONObject>> toResetPasswd(String username, String phoneno, String verifiycode) {
        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(phoneno) || StringUtil.isEmpty(verifiycode)) {
            return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultCode.ERROR_PARAM_ISNULL, ConstantProperties.ResultCode.ERROR_PARAM_ISNULL_VALUE);
        }
        try {
            String userInfo = jedisUtil.hget(REGIST_USERINFO_HASH, username);
            if (StringUtil.isEmpty(userInfo)) {//该用户没有注册
                ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_USER_NOTREGIST, ConstantProperties.ResultRegist.ERROR_USER_NOTREGISTDESC);
            }
            JSONObject jsonObject = JSONObject.parseObject(userInfo);
            if (!StringUtil.isNull(jsonObject)) {
                String phone = jsonObject.getString("phoneNo");
                if (!phone.equals(phoneno)) {
                    //输入的账户与手机号码 绑定不一致
                    return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ISBINDNOTMATCHCODE, ConstantProperties.ResultRegist.ISBINDNOTMATCHDESC);
                }
            }
            String verifiyCode = jedisUtil.getRedisStrValue(VERIFIYCODE + phoneno);
            if (!verifiyCode.equals(verifiycode)) {//验证码输入错误
                return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultRegist.ERROR_VERIFIYCODE, ConstantProperties.ResultRegist.ERROR_VERIFIYDESC);
            }

        } catch (SPTException e) {
            e.printStackTrace();
            return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultCode.ERROR_OTHER, ConstantProperties.ResultCode.ERROR_OTHER_DESC);

        }
        return ResponseModel.success();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", value = "用户名", name = "username", dataType = "string", required = true),
            @ApiImplicitParam(paramType = "query", value = "输入新密码", name = "passwd", dataType = "string", required = true),
            @ApiImplicitParam(paramType = "query", value = "再次输入密码", name = "confirmPasswd", dataType = "string", required = true)
    })
    @PostMapping(value = "resetPasswd", produces = "application/json;charset=utf-8")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseModel<JSONObject>> resetPasswd(String username, String passwd, String confirmPasswd) {
        if (StringUtil.isEmpty(passwd) || StringUtil.isEmpty(confirmPasswd)) {
            return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultCode.ERROR_PARAM_ISNULL, ConstantProperties.ResultCode.ERROR_PARAM_ISNULL_VALUE);
        }
        if (!passwd.equals(confirmPasswd)) {
            return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultCode.ERROR_INPUT_TWICECODE, ConstantProperties.ResultCode.ERROR_INPUT_TWICEDESC);
        }

        try {
           userDao.resetPasswd(username, passwd);
        } catch (SPTException e) {
            e.printStackTrace();
            return ResponseModel.error(HttpStatus.OK, ConstantProperties.ResultCode.ERROR_OTHER, ConstantProperties.ResultCode.ERROR_OTHER_DESC);
        }
        return ResponseModel.success();
    }
}
