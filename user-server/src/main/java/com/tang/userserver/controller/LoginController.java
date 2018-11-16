package com.tang.userserver.controller;

import com.tang.userserver.dao.UserDao;
import com.tang.userserver.entity.ResponseModel;
import com.tang.userserver.util.JedisUtil;
import io.swagger.annotations.*;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation(value = "通过用户名密码登陆",notes = "tangjiwnag")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",name = "username",dataType = "string",required = true)})
    public ResponseEntity<ResponseModel<JSObject>> toLoginFromUserPasswd() {
        return ResponseModel.success();
    }


    public ResponseEntity<ResponseModel<JSObject>> toLoginFromPhone() {
        return ResponseModel.success();
    }
}
