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
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            map.put(name, valueStr);
        }
        return map;
    }

}
