package com.springboot.example.util;

import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * 签名工具类
 *
 * @author zhangyonghong
 * @date 2019.7.8
 */
@Slf4j
public class SignUtil {

    private SignUtil() {
    }

    public static String generateSignature(Map<String, String> map) {
        Set<String> keySet = map.keySet();
        String[] keyArray = new String[keySet.size()];
        keySet.toArray(keyArray);
        Arrays.sort(keyArray);
        StringBuilder builder = new StringBuilder();

        for (String key : keyArray) {
            builder.append(key).append("=").append((map.get(key)).trim()).append("&");
        }

        try {
            return MD5Util.encode(builder.substring(0, builder.length() - 1).getBytes()).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            ErrorPrintUtil.printErrorMsg(log, e);
            return null;
        }
    }

}
