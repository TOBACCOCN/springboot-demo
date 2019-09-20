package com.springboot.example.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 签名工具类
 *
 * @author zhangyonghong
 * @date 2019.7.8
 */
public class SignUtil {

    public static String generateSignature(Map<String, String> map, String signFieldName) throws Exception {
        Set<String> keySet = map.keySet();
        String[] keyArray = new String[keySet.size()];
        keySet.toArray(keyArray);
        Arrays.sort(keyArray);
        StringBuilder builder = new StringBuilder();

        for (String key : keyArray) {
            if (!key.equals(signFieldName) && (map.get(key)).trim().length() > 0) {
                builder.append(key).append("=").append((map.get(key)).trim()).append("&");
            }
        }

        return MD5Util.encode(builder.substring(0, builder.length() - 1).getBytes()).toUpperCase();
    }

    public static boolean checkSign(Map<String, String> map, String signFieldName) throws Exception {
        if (!map.containsKey(signFieldName)) {
            return false;
        } else {
            String sign = map.get(signFieldName);
            return generateSignature(map, signFieldName).equals(sign);
        }
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("id", "resfrewsrf");
        map.put("name", "daffsaf");
        generateSignature(map, "sign");
    }

}
