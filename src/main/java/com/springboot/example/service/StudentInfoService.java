package com.springboot.example.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.springboot.example.domain.StudentInfo;

import java.util.List;

/**
 * 学生接口(oracle)
 *
 * @author TOBACCO
 * @date 2022.12.19
 */
public interface StudentInfoService {

    int create(StudentInfo studentInfo);

    StudentInfo findById(Long id);

    List<StudentInfo> findAll();

    IPage<StudentInfo> findPage(long current, long size);

}
