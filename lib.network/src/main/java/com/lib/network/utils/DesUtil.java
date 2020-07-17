package com.lib.network.utils;

import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName: DesUtil
 * @Description: 作用描述
 * @Author: CHIA
 * @CreateDate: 2020/7/17
 */
public class DesUtil {
    public static String calc(String dat, String key, String cid, String sid, String ver) {
        //拼接字符串
        String X = splicingString(dat, key, cid, sid, ver);
        //计算MD5
        String md5 = calcMD5(X);
        //转为大写
        String Y = generateY(md5);
        //生成byte数组
        byte[] yArray = getYArray(Y);
        //异或运算
        byte result = xorOperation(yArray);
        //转16进制
        String hex = toHexString(result);
        //转为大写
        String hexUpper = hexUpperCase(hex);
        println("hexUpper:" + hexUpper);
        return hexUpper;
    }


    @NonNull
    private static String hexUpperCase(String hex) {
        return hex.toUpperCase();
    }

    @NonNull
    private static String toHexString(byte result) {
        String hex = Integer.toHexString(result & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return hex;
    }

    private static byte xorOperation(byte[] yArray) {
        byte result = yArray[0];
        for (int i = 1; i < yArray.length; i++) {
            result = (byte) (result ^ yArray[i]);
        }
        return result;
    }

    private static byte[] getYArray(String y) {
        byte[] yArray = null;
        try {
            yArray = y.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return yArray;
    }

    @NonNull
    private static String generateY(String md5) {
        String Y = md5.toUpperCase();
        println("Y:" + Y);
        println("Y.length:" + Y.length());
        return Y;
    }

    private static String calcMD5(String x) {
        String md5 = MD5Util.md5(x);
        println("md5:" + md5);
        return md5;
    }

    @NonNull
    private static String splicingString(String dat, String key, String cid, String sid, String ver) {
        int len = dat.length();
        println("len:" + len);
        return len + key + cid + sid + ver;
    }

    public static void println(String str) {
        System.out.println("Z-Test: " + str);
    }
}
