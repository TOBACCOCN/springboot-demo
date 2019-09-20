package com.springboot.example.web.controller;

import com.springboot.example.domain.User;
import com.springboot.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * freemarker 控制器
 *
 * @author zhangyonghong
 * @date 2019.9.18
 */
@Controller
public class FreemarkerController {

    @Autowired
    private UserService userService;

    @GetMapping("/freemarker/users")
    // public String users(Model model) {
    public Mono<String> users(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        // return "/freemarker/users";
        return Mono.create(monoSink -> monoSink.success("users"));
    }

}
