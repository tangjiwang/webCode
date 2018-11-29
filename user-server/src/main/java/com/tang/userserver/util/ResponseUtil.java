package com.tang.userserver.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.tang.userserver.constant.ConstantProperties;
import com.tang.userserver.entity.ResponseModel;


/**
 * @Description: 公共返回
 * @Param:
 * @return:
 * @Author: tangjiwang
 * @Date: 2018/11/27
 */
public class ResponseUtil {

    /**
     * feign调用断路器统一回调
     *
     * @return
     */
    public static ResponseModel fallback() {

        return new ResponseModel(ConstantProperties.HttpStatus.HTTP_STATUS_500,
                ConstantProperties.ResponseDesc.FEIGN_REQ_ERROR_DESC, ConstantProperties.ResultCode.FEIGN_REQ_ERROR);
    }

    //值过滤器
    private static ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object obj, String s, Object v) {
            if (null == v) {
                return "";
            }
            return v;
        }
    };

}
