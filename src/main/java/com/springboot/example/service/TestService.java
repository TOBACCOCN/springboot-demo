package com.springboot.example.service;

/**
 * 测试接口
 *
 * @author zhangyonghong
 * @date 2022.7.23
 */
public interface TestService {

    int createEx(String name);

    int transactionNotWork(String name);

    int transactionWork(String name);

}
