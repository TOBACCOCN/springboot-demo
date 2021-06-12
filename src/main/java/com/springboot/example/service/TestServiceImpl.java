package com.springboot.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.example.dao.TestMapper;
import com.springboot.example.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * 学生接口实现
 *
 * @author zhangyonghong
 * @date 2019.9.19
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Transactional/*(propagation = Propagation.REQUIRES_NEW)*/
    public int create(Test test) throws RuntimeException {
        return testMapper.insert(test);
        // return createEx(test);
    }

    @Transactional
    public int createEx(Test test) throws RuntimeException {
        // int insert = testMapper.insert(test);
        int insert = create(test);
        try {
            FileInputStream fileInputStream = new FileInputStream("D:/TEST");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FILE_NOT_FOUND");
        }
        return insert;
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
