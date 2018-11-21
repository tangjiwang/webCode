package com.tang.userserver.util;

import java.awt.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

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

    /**
     * 把long time 转换为 YYYYMMDD时间
     */
    public static String longTimeToYMD(Long time) {
        return new SimpleDateFormat("YYYYMMdd").format(new Date(time));
    }


    public static void main(String[] args)  throws  Exception {
        System.out.println(longTimeToYMD(new Date().getTime()));

        //        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDD");
//        Date date = new Date();
//        Long time  = date.getTime();
//
//        System.out.println( simpleDateFormat.format(new Date().getTime()));
        //System.out.println( encrypt_MD5("123465aa"));
//        System.out.println(encrypt_Base64("123456aa"));
//        int a =   (int)(1+Math.random()*(2));
//        System.out.println(a);
//        String sources = "0123456789";
//        Random random = new Random();
//        //System.out.println(random.nextInt(9));
//        StringBuilder sb= new StringBuilder();
//        for (int i = 0; i < 106; i++) {
//            sb.append(sources.charAt(random.nextInt(10)) + "");
//        }
//        System.out.println(sb);
//
//        StringBuilder sb = new StringBuilder("WW");
//        Random random = new Random();
//        for (int i = 0; i <8 ; i++) {
//            sb.append(random.nextInt(10));
//        }
//        System.out.println(sb.toString());
    }
}
