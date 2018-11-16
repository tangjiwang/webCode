package com.tang.userserver.util;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 共通工具类. <br>
 * 项目中常见逻辑与功能的实现.
 * <p>
 * Copyright: Copyright (c) 2013-11-12 上午11:48:05
 * <p>
 * Company: 柠檬科技
 * <p>
 *
 * @author lurufeng
 * @version 1.0.0
 */
public class CommonUtil {

    private static final Logger LOGGER = LogManager.getLogger(CommonUtil.class.getName());

    /**
     * @param start 开始时间
     * @param end   结束时间
     * @return
     * @Title: getTimeInterval
     * @Description: 计算时间差
     * @date: 2016年12月5日
     * @author gumingchao
     */
    public static String getTimeInterval(String start, String end) {
        if (StringUtils.isEmpty(start) || StringUtils.isEmpty(end))
            return "";
        StringBuilder res = new StringBuilder("");
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date sTime = df.parse(start);
            Date eTime = df.parse(end);

            long l = eTime.getTime() - sTime.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            long hour = (l / (60 * 60 * 1000) - day * 24);
            long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            if (day > 0) {
                res.append(day + "天");
            }
            if (hour > 0) {
                res.append(hour + "小时");
            }
            res.append(min + "分" + s + "秒");
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return res.toString();
    }

    /**
     * @param json
     * @return
     * @Title: convertJsonToMap
     * @Description: 将json转为map
     * @date: 2016年11月2日
     * @author gumingchao
     */
    public static Map<String, String> convertJsonToMap(JSONObject json) {
        ListOrderedMap map = new ListOrderedMap();
        for (Object k : json.keySet()) {
            Object v = json.get(k);
            // 如果内层还是数组的话，继续解析
            if (v instanceof JSONArray) {
                List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                Iterator<Object> it = ((JSONArray) v).iterator();
                while (it.hasNext()) {
                    Object json2 = it.next();
                    list.add(convertJsonToMap((JSONObject) json2));
                }
                map.put(k.toString(), list);
            } else {
                map.put(k.toString(), String.valueOf(v));
            }
        }
        return map;
    }

    /**
     * 方法描述:将bean转换成map
     *
     * @param
     * @return date:2015年9月18日
     * @author gumingchao
     */
    public static Map<String, Object> convertBeanToMap(Object bean)
            throws IllegalArgumentException, IllegalAccessException {
        Field[] fields = bean.getClass().getDeclaredFields();
        HashMap<String, Object> data = new HashMap<String, Object>();
        for (Field field : fields) {
            field.setAccessible(true);
            data.put(field.getName(), field.get(bean));
        }
        return data;
    }

    /**
     * @return
     * @Title: randomNumber
     * @Description: 生成一个num位随机数字
     * @date: 2016年9月19日
     * @author gumingchao
     */
    public static String randomNumber(int num) {
        String chars = "0123456789";
        char[] rands = new char[num];
        for (int i = 0; i < num; i++) {
            int rand = (int) (Math.random() * 10);
            rands[i] = chars.charAt(rand);
        }
        return String.valueOf(rands);
    }

    /**
     * 方法描述:判断一个字符串是否为null或空字符串（被trim后为空字符串的也算）。
     *
     * @param str 需要判断的字符串
     * @return false：不是空字符串，true：是空字符串 date:2013-11-12 add by: liu_tao@xwtec.cn
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 方法描述:将null转换成空字符串
     *
     * @param str-原始字符串
     * @return String-转换后的字符串 date:2013-11-13 add by: chenxiang@xwtec.cn
     */
    public static String changeNullToEmpty(String str) {
        if (isEmpty(str)) {
            return "";
        } else {
            return str;
        }
    }

    /**
     * 方法描述:null转为"" 非null的对象转化为String
     *
     * @param obj
     * @return obj.toString date:Nov 26, 2013 add by: houxu@xwtec.cn
     */
    public static String changeNullToEmptyString(Object obj) {
        if (obj == null)
            return "";
        else
            return obj.toString();
    }

    /**
     * 方法描述:对一个字符串进行MD5加密
     *
     * @param sourceStr - 原始字符串
     * @return String - 加密后的长度为32的字符串（如果原始字符串为null或"",该方法返回null） date:2013-11-13
     * add by: chenxiang@xwtec.cn
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5(String sourceStr) throws NoSuchAlgorithmException {
        if (sourceStr == null || "".equals(sourceStr.trim())) {
            return null;
        }
        byte[] source = sourceStr.getBytes();
        String s = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数， 用字节表示就是 16 个字节
            char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符， 所以表示成 16
            // 进制需要 32 个字符
            int k = 0; // 表示转换结果中对应的字符位置
            for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节 ,转换成 16
                // 进制字符的转换
                byte byte0 = tmp[i]; // 取第 i 个字节
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];// 取字节中高 4 位的数字转换, >>>
                // 为逻辑右移，将符号位一起右移
                str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
            }
            s = new String(str); // 换后的结果转换为字符串
        } catch (NoSuchAlgorithmException e) {
            throw e;
        }
        return s;
    }

    /**
     * 方法描述:获取格式化后的异常信息
     *
     * @param stackTraceElement 用来获取类名与方法名的栈信息
     * @param e                 异常对象
     * @return 格式化后的异常信息 date:2014-2-20 add by: liu_tao@xwtec.cn
     */
    public static String getErrorMessage(StackTraceElement stackTraceElement, Exception e) {
        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(e.toString() + "\n");
        StackTraceElement[] stacks = e.getStackTrace();
        if (stacks != null) {
            for (StackTraceElement stack : stacks) {
                errorMessage.append("\t" + stack.toString() + "\n");
            }
        }
        return getErrorMessage(stackTraceElement, errorMessage.toString());
    }

    /**
     * 方法描述:获取格式化后的异常信息
     *
     * @param stackTraceElement 用来获取类名与方法名的栈信息
     * @param errorMessage      格式化前的异常信息
     * @return 格式化后的异常信息 date:2014-4-8 add by: liu_tao@xwtec.cn
     */
    public static String getErrorMessage(StackTraceElement stackTraceElement, String errorMessage) {
        StringBuilder result = new StringBuilder();
        result.append("[" + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName()
                + "]:failed. throw e:" + errorMessage);
        return result.toString();
    }

    // 生成id 15位
    public static String getSeq(String prex) {
        String seq = "", now = "";
        now = new SimpleDateFormat("yyMMdd").format(new Date());
        String tamp = String.valueOf(System.currentTimeMillis()).substring(7);
        seq = prex + now + tamp;
        return seq;
    }

    /*
     * 判断数组中是否有重复的值 true不重复、false重复
     */
    public static boolean isRepeat(JSONArray data) {
        Set<String> set = new HashSet<String>();
        for (Object str : data) {
            set.add((String) str);
        }
        if (set.size() == data.size()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 将JSONObject转换为map
     *
     * @param jsonObject
     * @return
     */
    public static Map<String, Object> convertJson2Map(JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(jsonObject.toJSONString(), new TypeReference<HashMap<String, Object>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 将map转换为json
     *
     * @param map
     * @return
     */
    public static JSONObject covertMapToJSON(Map<String, ?> map) {
        JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(map));
        return json;
    }

    /**
     * 组装请求体
     *
     * @param invokerId
     * @param invokerType
     * @param data
     * @return
     */
    public static JSONObject buildRequestEntity(String invokerId, String invokerType, JSONObject data) {
        JSONObject body = new JSONObject();

        JSONObject header = new JSONObject();
        header.put("uuid", UUID.randomUUID());
        header.put("invokerId", invokerId);
        header.put("invokerType", invokerType);

        body.put("header", header);
        body.put("data", data);
        LOGGER.info("feign request param" + body);
        return body;
    }


    /**
     * 将传过来的默认的0000-00-00 00:00:00或者0000-00-00时间转为null
     *
     * @param sourceDate
     */
    public static String turnZeroDateToNull(String sourceDate) {
        if (sourceDate.equals("0000-00-00 00:00:00") || sourceDate.equals("0000-00-00")) {
            sourceDate = null;
        }
        return sourceDate;
    }

}
