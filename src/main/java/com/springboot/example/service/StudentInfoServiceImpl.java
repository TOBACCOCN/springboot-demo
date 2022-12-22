package com.springboot.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.example.dao.oracle.StudentInfoMapper;
import com.springboot.example.datasource.DynamicDataSourceNameEnum;
import com.springboot.example.datasource.MyDataSource;
import com.springboot.example.domain.StudentInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 学生接口实现(oracle)
 *
 * @author TOBACCO
 * @date 2022.12.19
 */
@Service
@Transactional
@RequiredArgsConstructor
// @DS("oracle")
public class StudentInfoServiceImpl implements StudentInfoService {

    private final StudentInfoMapper studentInfoMapper;

    public int create(StudentInfo studentInfo) {
        return studentInfoMapper.insert(studentInfo);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @MyDataSource(DynamicDataSourceNameEnum.ORACLE)
    public StudentInfo findById(Long id) {
        return studentInfoMapper.selectById(id);
    }

    public List<StudentInfo> findAll() {
        return studentInfoMapper.selectList(new QueryWrapper<>());
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @MyDataSource(DynamicDataSourceNameEnum.ORACLE)
    public IPage<StudentInfo> findPage(long current, long size) {
        // sharding-jdbc 分页暂时有问题，https://github.com/apache/incubator-shardingsphere/issues/2926
        Page<StudentInfo> page = new Page<>(current, size);
        return studentInfoMapper.selectPage(page, new QueryWrapper<>());
    }

}
