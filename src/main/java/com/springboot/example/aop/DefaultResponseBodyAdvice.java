package com.springboot.example.aop;

import com.alibaba.fastjson.JSON;
import com.springboot.example.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


@Component
@RestControllerAdvice("com.springboot.example.web.controller")
@Slf4j
public class DefaultResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;    // 注意这里要返回true, 不能是默认的 false
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        try {
            log.info(">>>>> RESPONSE: [{}]", JSON.toJSONString(o));
            return o;
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(log, e);
        }
        return o;
    }
}
