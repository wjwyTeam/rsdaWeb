/*
 * @Descripttion: 
 * @version: v0.0.1
 * @Author: ZHANGQI
 * @Date: 2019-12-05 14:39:26
 * @LastEditors: ZHANGQI
 * @LastEditTime: 2019-12-05 14:41:11
 */
package com.wjwy.rsda.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description: MD5工具类
 * @author: ZHANGQI
 * @Date: 2019-01-15 13:35
 * @Version v1.0
 **/
public class MD5Util {

    /**
     * 将源字符串使用MD5加密为字节数组
     * @param source
     * @return
     */
    public static byte[] md5ToBytes(String source) {
        byte[] result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(source.getBytes("UTF-8"));
            result = md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 将源字符串使用MD5加密为32位16进制数
     * @param source
     * @return
     */
    public static String md5ToHex(String source) {
        byte[] data = md5ToBytes(source);
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(0xff & data[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
