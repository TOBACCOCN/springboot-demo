package com.springboot.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {

    private static Logger logger = LoggerFactory.getLogger(HelloController.class);

    // GET 请求
    @GetMapping("/hello")
    // 不加 @ResponseBody 就会被 thymeleaf 视图解析器解析到 html 页面
    @ResponseBody
    public Object helloGET(HttpServletRequest request) {
        Map<String, String> map = getMap(request.getParameterMap());
        logger.info(">>>>> PARAM_MAP: {}", map);
        return map;
    }

    private Map<String, String> getMap(Map<String, String[]> paramMap) {
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

    // POST（x-www-form-urlencoded） 请求
    @PostMapping("/hello")
    @ResponseBody
    public Object helloPOST(HttpServletRequest request) {
        Map<String, String> map = getMap(request.getParameterMap());
        logger.info(">>>>> PARAM_POST: {}", map);
        return map;
    }

    // POST（x-www-form-urlencoded） 请求
    @PostMapping("/helloUSER")
    @ResponseBody
    public Object helloPOST(User user) {
        logger.info(">>>>> PARAM_POST_USER {}", user);
        return user;
    }

    // POST（application/json） 请求
    @PostMapping("/helloJSON")
    @ResponseBody
    public Object helloPOST(@RequestBody Map<String, Object> map) {
        logger.info(">>>>> PARAM_POST_JSON: {}", map);
        return map;
    }

}
