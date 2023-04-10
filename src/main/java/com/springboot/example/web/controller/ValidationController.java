package com.springboot.example.web.controller;

import com.springboot.example.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * validation 请求控制器
 *
 * @author zhangyonghong
 * @date 2019.6.1
 */
@Validated
@RestController
@RequestMapping("/validation")
@Slf4j
public class ValidationController {

    @GetMapping("/single")
    @ResponseBody
    public Object single(@NotBlank(message = "姓名不能为空") String name,
                         @Digits(integer = 3, fraction = 0, message = "年龄")
                         @Min(value = 10, message = "年龄最小 10 岁")
                         @Max(value = 128, message = "年龄最大 128 岁") int age) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        return user;
    }

    @PostMapping("/form")
    @ResponseBody
    public Object form(@Validated User user) {
        return user;
    }

    @PostMapping("/json")
    @ResponseBody
    public Object json(@RequestBody @Validated User user) {
        return user;
    }

}
