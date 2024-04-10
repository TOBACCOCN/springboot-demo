package com.springboot.example.service;

import com.springboot.example.dao.mysql.TestMapper;
import com.springboot.example.domain.Test;
import com.springboot.example.util.SimpleApplicationContextAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * 测试接口实现
 *
 * @author zhangyonghong
 * @date 2022.7.23
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Transactional/*(propagation = Propagation.REQUIRES_NEW)*/
    public int create(Test test) {
        return testMapper.insert(test);
    }

    @Transactional
    public int createEx(String name) {
        int insert = testMapper.insert(new Test((long) (Math.random()* 1000), name));
        try (FileInputStream ignored = new FileInputStream("D:/TEST")) {
            log.info("NO EXCEPTION OCCUR");
        } catch (IOException e) {
            throw new MyRuntimeException("FILE_NOT_FOUND");
        }
        return insert;
    }

    class MyRuntimeException extends RuntimeException {

        public MyRuntimeException(String message) {
            super(message);
        }

    }

    // 在非事务方法中直接调用事务方法 createEx()，createEx() 的事务失效
    public int transactionNotWork(String name) {
        return createEx(name);
    }

    // 通过 spring 容器拿到当前类对象(即为代理对象)使事务生效
    public int transactionWork(String name) {
        return SimpleApplicationContextAware.getApplicationContext().getBean(TestService.class).createEx(name);
    }

}
