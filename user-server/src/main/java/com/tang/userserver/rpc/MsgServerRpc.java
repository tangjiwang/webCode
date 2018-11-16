package com.tang.userserver.rpc;

import com.alibaba.fastjson.JSONObject;
import com.tang.userserver.entity.ResponseModel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: gitCode
 * @description: 消息中心系统
 * @author: tangjiwang
 * @Param $
 * @create: 2018-11-15 14:55
 **/
@FeignClient(value = "MSG-SERVER",url = "http://localhost:48080")
public interface MsgServerRpc {
    @RequestMapping(value = "/sendSms",method = RequestMethod.POST)
    ResponseModel<JSONObject> sendSms(JSONObject  jsonObject);
}
