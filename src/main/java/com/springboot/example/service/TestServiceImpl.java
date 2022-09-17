package com.springboot.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.example.dao.TestMapper;
import com.springboot.example.domain.Test;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * 学生接口实现
 *
 * @author zhangyonghong
 * @date 2019.9.19
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Transactional/*(propagation = Propagation.REQUIRES_NEW)*/
    public int create(Test test) {
        return testMapper.insert(test);
        // return createEx(test);
    }

    @Transactional
    public void test() {
        createEx(new Test(23L, "hello"));
    }

    @Transactional
    public int createEx(Test test) {
        // int insert = testMapper.insert(test);
        int insert = create(test);
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

    public Test findById(Long id) {
        return testMapper.selectById(id);
    }

    public List<Test> findAll() {
        return testMapper.selectList(new QueryWrapper<>());
    }

    public IPage<Test> findPage(long current, long size) {
        // sharding-jdbc 分页暂时有问题，https://github.com/apache/incubator-shardingsphere/issues/2926
        Page<Test> page = new Page<>(current, size);
        return testMapper.selectPage(page, new QueryWrapper<>());
    }

}
