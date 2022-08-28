package com.springboot.example.dao;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class StudentInfoMapperTest extends TestCase {

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Test
    public void testSelectById() {
        Map<String, Object> map = studentInfoMapper.selectById(1);
        log.info(">>>>> MAP: [{}]", map);
        String name = (String) map.get("NAME");
        log.info(">>>>> NAME: [{}]", name);
    }

}