package com.springboot.example.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 学生接口单元测试
 *
 * @author zhangyonghong
 * @date 2019.9.19
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class TestServiceTest {

    @Autowired
    private TestService testService;

    @Test
    // @Transactional
    public void insert() {
        com.springboot.example.domain.Test test = new com.springboot.example.domain.Test();
        test.setId(1000001L);
        test.setName("zhouba");
        int insertAffect = testService.createEx(test);
        // test.setId(2L);
        // insertAffect = testService.createEx(test);
        log.info(">>>>> INSERT_AFFECT: [{}]", insertAffect);
    }

}
