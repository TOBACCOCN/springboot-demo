package com.springboot.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.springboot.example.domain.Test;

import java.util.List;

/**
 * 学生接口
 *
 * @author zhangyonghong
 * @date 2019.9.19
 */
public interface TestService {

    int create(Test test) throws RuntimeException;

    int createEx(Test test) throws RuntimeException;

    Test findById(Long id);

    List<Test> findAll();

    IPage<Test> findPage(long current, long size);

}
