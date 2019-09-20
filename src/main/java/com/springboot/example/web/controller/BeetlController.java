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
 * beetl 控制器
 *
 * @author zhangyonghong
 * @date 2019.9.18
 */
@Controller
public class BeetlController {

    @Autowired
    private UserService userService;

    @GetMapping("/beetl/users")
    // public String users(Model model) {
    public Mono<String> users(ModelMap modelMap) {
        List<User> users = userService.findAll();
        modelMap.addAttribute("users", users);
        // return "/beetl/users";
        return Mono.create(monoSink -> monoSink.success("users"));
    }

}
