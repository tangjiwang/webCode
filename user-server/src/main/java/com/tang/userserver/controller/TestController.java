package com.tang.userserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @program: gitCode
 * @description: 测试
 * @author: tangjiwang
 * @Param $
 * @create: 2018-11-09 17:26
 **/
@RestController
public class TestController {

    @Value("${spring.redis.password}")
    String passwd;

    @PostMapping("/test")
    public String test() {
        return passwd;
    }

    @RequestMapping(value = "/jsontest",method = RequestMethod.POST)
    public JSONObject jsonTest()
    {


    String str = "'' b f2 cf 3d 4";
        String[] s = str.trim().split(" ");
    List<LinkedHashMap<String,Object>> list = new LinkedList<>();
        for (int i = 0; i < s.length/2; i++) {
            LinkedHashMap<String,Object> map = new LinkedHashMap();
            map.put("key",s[2*i]);
            map.put("value",s[2*i+1]);
            list.add(map);
        }
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("data",list);

        return jsonObject1 ;

    }

    @PostMapping(value = "/do",produces = "application/json;charset=UTF-8")
    public String test1(@RequestBody JSONObject jsonObject)
    {
        String str = "";
        StringBuilder sb = new StringBuilder(str);
        if(null != jsonObject)
        {
            JSONArray jsonArray1 =jsonObject.getJSONArray("data");
            //JSONArray jsonArray1 = JSONArray.parseArray(jsonArray.toString());

            for (int i = 0; i <jsonArray1.size() ; i++) {
                JSONObject jsono =JSONObject.parseObject(jsonArray1.get(i).toString());
                String loginValue = jsono.getString("logic")==null?"":jsono.getString("logic");
                String featureValue = jsono.getString("feature")==null?"":jsono.getString("feature");
                sb.append(loginValue+" "+featureValue+" ");
            }
        }
        return sb.toString().trim();
    }

}
