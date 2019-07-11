package com.springboot.example.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数工具类
 *
 * @author zhangyonghong
 * @date 2019.6.14
 */
public class ParamUtil {

    public static Map<String, String> getMap(Map<String, String[]> paramMap) {
        Map<String, String> map = new HashMap<>();
        for (String name : paramMap.keySet()) {
            String[] values = paramMap.get(name);
            StringBuilder builder = new StringBuilder();
            for (String value : values) {
                builder.append(value).append(",");
            }
            map.put(name, builder.substring(0, builder.length() -1));
        }
        return map;
    }

}
