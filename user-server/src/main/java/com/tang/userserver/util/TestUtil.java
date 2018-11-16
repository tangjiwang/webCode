package com.tang.userserver.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * @program: gitCode
 * @description: 测试工具类
 * @author: tangjiwang
 * @Param $
 * @create: 2018-11-16 17:05
 **/
public class TestUtil {
    /***
     * MD5加密
     * @param str 需要加密的参数
     * @return
     * @throws Exception
     */
    public static String encrypt_MD5(String str) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        return new BigInteger(1,md.digest()).toString(
                16
        );
    }

    /***
     * Base64加密
     * @param str 需要加密的参数
     * @return
     * @throws Exception
     */
    public static String encrypt_Base64(String str) throws Exception {
        String result = Base64.getEncoder().encodeToString(str.getBytes("UTF-8"));
        return result;
    }

    /***
     * Base64解密
     * @param str 需要解密的参数
     * @return
     * @throws Exception
     */
    public static String decrypt_Base64(String str) throws Exception {
        byte[] asBytes = Base64.getDecoder().decode(str);
        String result = new String(asBytes,"UTF-8");
        return result;
    }

    public static void main(String[] args)  throws  Exception {
        //System.out.println( encrypt_MD5("123465aa"));
        System.out.println(encrypt_Base64("123456aa"));
    }
}
