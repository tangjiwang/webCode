package com.tang.userserver.hystrix;

import com.alibaba.fastjson.JSONObject;
import com.tang.userserver.entity.ResponseModel;
import com.tang.userserver.rpc.MsgServerRpc;
import com.tang.userserver.util.ResponseUtil;
import org.springframework.stereotype.Component;

/**
 * @description: 实现类，
 * @author: tangjiwang
 * @Param $
 * @create: 2018-11-27 09:28
 **/
@Component
public class MsgServerRpcHystrix implements MsgServerRpc {
    @Override
    public ResponseModel<JSONObject> sendSms(JSONObject jsonObject) {
        return ResponseUtil.fallback();
    }
}
