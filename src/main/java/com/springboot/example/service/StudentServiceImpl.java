package com.springboot.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.example.dao.mysql.StudentMapper;
import com.springboot.example.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 学生接口实现
 *
 * @author zhangyonghong
 * @date 2019.9.19
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    // student 表不分片

    @Autowired
    private StudentMapper studentMapper;

    public int create(Student student) {
        return studentMapper.insert(student);
    }

    public Student findById(Long id) {
        return studentMapper.selectById(id);
    }

    public List<Student> findAll() {
        return studentMapper.selectList(new QueryWrapper<>());
    }

    public IPage<Student> findPage(long current, long size) {
        // sharding-jdbc 分页暂时有问题，https://github.com/apache/incubator-shardingsphere/issues/2926
        Page<Student> page = new Page<>(current, size);
        return studentMapper.selectPage(page, new QueryWrapper<>());
    }

}
