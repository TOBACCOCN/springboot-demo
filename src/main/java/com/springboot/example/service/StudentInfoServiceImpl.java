package com.springboot.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.example.dao.oracle.StudentInfoMapper;
import com.springboot.example.datasource.DynamicDataSourceNameEnum;
import com.springboot.example.datasource.MyDataSource;
import com.springboot.example.domain.StudentInfo;
import com.springboot.example.util.AESUtil;
import com.springboot.example.util.ErrorPrintUtil;
import com.springboot.example.util.MD5Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * 学生接口实现(oracle)
 *
 * @author TOBACCO
 * @date 2022.12.19
 */
@Slf4j
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
        if ((id & 1) == 1) {
            String encrypt = AESUtil.encrypt(String.valueOf(id));
            log.info(">>>>> ENCRYPT: [{}]", encrypt);
        } else {
            try {
                String encoded = MD5Util.encode(String.valueOf(id).getBytes());
                log.info(">>>>> ENCODED: [{}]", encoded);
            } catch (NoSuchAlgorithmException e) {
                ErrorPrintUtil.printErrorMsg(log, e);
            }
        }

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
