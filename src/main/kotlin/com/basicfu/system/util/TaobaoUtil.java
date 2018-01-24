package com.basicfu.system.util;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;

/**
 * @author basicfu
 * @date 2018/1/23
 */
public class TaobaoUtil {
    private static String CHARSET="UTF-8";
    public static String signTopRequest(Map<String, String> params, String secret) throws IOException {
        return signTopRequest(params,secret,"hmac");
    }
    public static String signTopRequest(Map<String, String> params, String secret, String signMethod) throws IOException {
        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);
        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        if ("md5".equals(signMethod)) {
            query.append(secret);
        }
        for (String key : keys) {
            String value = params.get(key);
            if (!key.isEmpty()&&!value.isEmpty()) {
                query.append(key).append(value);
            }
        }
        // 第三步：使用MD5/HMAC加密
        byte[] bytes;
        if ("hmac".equals(signMethod)) {
            bytes = encryptHMAC(query.toString(), secret);
        } else {
            query.append(secret);
            bytes = encryptMD5(query.toString());
        }

        // 第四步：把二进制转化为大写的十六进制(正确签名应该为32大写字符串，此方法需要时使用)
        return byte2hex(bytes);
    }

    private static byte[] encryptHMAC(String data, String secret) throws IOException {
        byte[] bytes = null;
        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes(CHARSET), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes());
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }

    private static byte[] encryptMD5(String data) throws IOException {
        return encryptMD5(data.getBytes(CHARSET));
    }
    private static byte[] encryptMD5(byte[] data) throws IOException {
        Object var1 = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(data);
            return bytes;
        } catch (GeneralSecurityException var3) {
            throw new IOException(var3.toString());
        }
    }
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
}
