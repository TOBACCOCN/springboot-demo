package com.springboot.example.web.controller;

import com.springboot.example.domain.User;
import com.springboot.example.util.ParamUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * http 请求控制器
 *
 * @author zhangyonghong
 * @date 2019.6.1
 */
@RestController
@RequestMapping("/simple")
@Slf4j
public class SimpleController {

    @PostMapping("/echo")
    @ResponseBody
    public Object echo(@RequestBody @Validated User user) {
        return user;
    }

}
