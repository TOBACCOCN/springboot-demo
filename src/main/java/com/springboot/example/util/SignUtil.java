package com.springboot.example.util;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * 签名工具类
 *
 * @author zhangyonghong
 * @date 2019.7.8
 */
public class SignUtil {

    public static String generateSignature(Map<String, String> map) throws Exception {
        Set<String> keySet = map.keySet();
        String[] keyArray = new String[keySet.size()];
        keySet.toArray(keyArray);
        Arrays.sort(keyArray);
        StringBuilder builder = new StringBuilder();

        for (String key : keyArray) {
            builder.append(key).append("=").append((map.get(key)).trim()).append("&");
        }

        return MD5Util.encode(builder.substring(0, builder.length() - 1).getBytes()).toUpperCase();
    }

}
