package com.springboot.example.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.springboot.example.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 用户控制器
 *
 * @author zhangyonghong
 * @date 2019.7.16
 */
@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ResponseBody
    // public Object findById(@RequestBody Map<String, String> paramMap) {
    public Mono<Object> login(@RequestBody Map<String, String> paramMap) {
        log.debug(">>>>> LOGIN_PARAM: [{}]", paramMap);

        // return userService.findById(id);
        Subject subject = SecurityUtils.getSubject();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "LOGIN_SUCCESS");

        try {
            subject.login(new UsernamePasswordToken(paramMap.get("username"), paramMap.get("password")));
        } catch (UnknownAccountException e) {
            jsonObject.put("result", "UNKNOWN_ACCOUNT");
        } catch (IncorrectCredentialsException e) {
            jsonObject.put("result", "INCORRECT_CREDENTIALS");
        } catch (LockedAccountException e) {
            jsonObject.put("result", "LOCKED_ACCOUNT");

        }

        subject.getSession().setAttribute("LOGGED", true);
        return Mono.create(monoSink -> monoSink.success(jsonObject));
        // return jsonObject;
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    // public Object findById(@PathVariable("id") Long id) {
    public Mono<Object> findById(@PathVariable("id") Long id) {
        // return userService.findById(id);
        return Mono.create(monoSink -> monoSink.success(userService.findById(id)));
    }

    @GetMapping("/users")
    @ResponseBody
    // public Object findAll() {
    public Mono<Object> findAll() {
        // return userService.findAll();
        return Mono.create(monoSink -> monoSink.success(userService.findAll()));
    }

    @GetMapping("/usersByOffsetLimit")
    @ResponseBody
    // public Object findAll(long offset, long limit) {
    public Mono<Object> findPage(long offset, long limit) {
        // return userService.findPage(offset, limit);
        return Mono.create(monoSink -> monoSink.success(userService.findPage(offset, limit)));
    }

}
