package com.springboot.example.web.controller;

import com.springboot.example.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@Controller
public class ThymeleafController {

    @GetMapping("/thymeleaf/user")
    // public String user(Model model) {
    public Mono<String> user(Model model) {
        User user = new User(127L, "zyh", 32, "ez");
        model.addAttribute("user", user);
        // return "/user";
        return Mono.create(monoSink -> monoSink.success("user"));
    }

}
