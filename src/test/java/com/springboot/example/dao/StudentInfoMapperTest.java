package com.springboot.example.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springboot.example.domain.StudentInfo;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * 学生 DAO 单元测试
 *
 * @author zhangyonghong
 * @date 2022.8.14
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class StudentInfoMapperTest extends TestCase {

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Test
    public void testSelectById() {
        Map<String, Object> map = studentInfoMapper.selectById(1);
        log.info(">>>>> MAP: [{}]", map);   // MAP: [{sex=1, name=zhangsan, createdate=2022/08/14, id=1}]
        String name = (String) map.get("NAME");
        log.info(">>>>> NAME: [{}]", name);     // NAME: [null]
    }

    @Test
    public void selectList() {
        List<StudentInfo> studentInfos = studentInfoMapper.selectList(new QueryWrapper<>());
        log.info(">>>>> studentInfos: [{}]", studentInfos);
    }

}