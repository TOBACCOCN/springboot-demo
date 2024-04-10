package com.springboot.example.web.controller;

import com.springboot.example.domain.User;
import com.springboot.example.service.UserService;
import com.springboot.example.util.ParamUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * http 请求控制器
 *
 * @author zhangyonghong
 * @date 2019.6.1
 */
@Controller
@Api("springboot example api")
@Slf4j
@RequiredArgsConstructor
public class HelloController {

    // private static Logger logger = LoggerFactory.getLogger(HelloController.class);

    private final UserService userService;

    @ApiOperation(value = "GET 请求")
    @RequiresPermissions("hello")
    // GET 请求
    @GetMapping("/batch/hello")
    // 不加 @ResponseBody 就会被 thymeleaf 视图解析器解析到 html 页面
    @ResponseBody
    public Object helloGET(HttpServletRequest request) {
    // public Mono<Object> helloGET(HttpServletRequest request) throws InterruptedException {
        Map<String, String> map = ParamUtil.getMap(request.getParameterMap());
        log.info(">>>>> PARAM_MAP: [{}]", map);
        map.put("hello", "java");
        userService.findAll();
        return map;
        // TimeUnit.DAYS.sleep(4);
        // return Mono.create(monoSink -> monoSink.success(map));
    }

    @ApiOperation(value = "POST（application/x-www-form-urlencoded）请求")
    // POST（application/x-www-form-urlencoded） 请求
    @PostMapping("/hello")
    @ResponseBody
    public Object helloPOST(HttpServletRequest request) {
    // public Mono<Object> helloPOST(HttpServletRequest request) {
        log.info(">>>>> REQUEST_URI: [{}]", request.getRequestURI());
        getHeaderMap(request).forEach((key, value) -> log.info(">>>>> HEADER_MAP: [{}] = [{}]", key, value));
        Map<String, String> map = ParamUtil.getMap(request.getParameterMap());
        log.info(">>>>> PARAM_POST: [{}]", map);
        return map;
        // return Mono.create(monoSink -> monoSink.success(map));
    }

    private Map<String, String> getHeaderMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            map.put(name, request.getHeader(name));
        }
        return map;
    }

    @ApiOperation(value = "POST（用封装对象接收请求参数）请求")
    // POST（application/x-www-form-urlencoded） 请求
    @PostMapping("/helloUSER")
    @ResponseBody
    public Object helloPOST(HttpServletRequest request, User user) {
    // public Mono<Object> helloPOST(HttpServletRequest request, User user) {
        getHeaderMap(request).forEach((key, value) -> log.info(">>>>> HEADER_MAP: [{}] = [{}]", key, value));
        log.info(">>>>> PARAM_POST_USER: [{}]", user);
        return user;
        // return Mono.create(monoSink -> monoSink.success(user));
    }

    @ApiOperation(value = "POST（application/json）请求")
    // POST（application/json） 请求
    @PostMapping("/helloJSON")
    @ResponseBody
    public Object helloPOST(HttpServletRequest request, @RequestBody Map<String, Object> map) {
        getHeaderMap(request).forEach((key, value) -> log.info(">>>>> HEADER_MAP: [{}] = [{}]", key, value));
        log.info(">>>>> PARAM_POST_JSON: [{}]", map);
        return map;
    }

}
