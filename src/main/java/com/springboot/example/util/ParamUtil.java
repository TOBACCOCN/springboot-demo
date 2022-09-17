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

    private ParamUtil(){
    }

    public static Map<String, String> getMap(Map<String, String[]> paramMap) {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
            String[] values = entry.getValue();
            StringBuilder builder = new StringBuilder();
            for (String value : values) {
                builder.append(value).append(",");
            }
            map.put(entry.getKey(), builder.substring(0, builder.length() -1));
        }
        return map;
    }

}
