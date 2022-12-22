package com.springboot.example.aop;

import com.springboot.example.datasource.DynamicDataSource;
import com.springboot.example.datasource.MyDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * aspect 暂不支持根据类上的注解织入，只能根据方法上的注解织入
 *
 * @author TOBACCO
 * @date 2022.12.22
 */
@Slf4j
@Aspect
@Component
public class DataSourceAspect {

    @Before("@annotation(com.springboot.example.datasource.MyDataSource)")
    public void before(JoinPoint point) {
        MyDataSource annotation = ((MethodSignature) point.getSignature()).getMethod().getAnnotation(MyDataSource.class);
        if (annotation != null)  {
            DynamicDataSource.setDataSource(annotation.value());
            log.info(">>>>> USING_DATA_SOURCE: [{}]", annotation.value());
        }
    }

    @After("@annotation(com.springboot.example.datasource.MyDataSource)")
    public void after() {
        DynamicDataSource.clearDataSource();
    }

}
