package com.springboot.example.web.controller;

import com.springboot.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

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
    public Mono<Object> findByOffsetLimit(long offset, long limit) {
        // return userService.find(offset, limit);
        return Mono.create(monoSink -> monoSink.success(userService.find(offset, limit)));
    }

}
