package com.springboot.example.web.controller;

import com.springboot.example.domain.User;
import com.springboot.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * enjoy 控制器
 *
 * @author zhangyonghong
 * @date 2019.9.19
 */
@Controller
public class EnjoyController {

    @Autowired
    private UserService userService;

    @GetMapping("/enjoy/users")
    // public String users(Model model) {
    public Mono<String> users(ModelMap modelMap) {
        List<User> users = userService.findAll();
        modelMap.addAttribute("users", users);
        // return "/enjoy/users";
        return Mono.create(monoSink -> monoSink.success("users"));
    }

}
