package com.tang.userserver.rpc;

import com.alibaba.fastjson.JSONObject;
import com.tang.userserver.entity.ResponseModel;
import com.tang.userserver.hystrix.MsgServerRpcHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: gitCode
 * @description: 发送消息中心系统
 * @author: tangjiwang
 * @Param $
 * @create: 2018-11-15 14:55
 **/
@FeignClient(value = "MSG-SERVER",url = "http://localhost:48080",fallback = MsgServerRpcHystrix.class)
public interface MsgServerRpc {
    @RequestMapping(value = "/registLogin/sendSms",method = RequestMethod.POST)
    ResponseModel<JSONObject> sendSms(JSONObject  jsonObject);
}
