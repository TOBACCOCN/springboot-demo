package com.springboot.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.springboot.example.domain.Student;

import java.util.List;

/**
 * 学生接口
 *
 * @author zhangyonghong
 * @date 2019.9.19
 */
public interface StudentService {

    int create(Student student);

    Student findById(Long id);

    List<Student> findAll();

    IPage<Student> findPage(long current, long size);

}
