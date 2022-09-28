package com.springboot.example.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 接口单元测试
 *
 * @author zhangyonghong
 * @date 2022.7.23
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class TestServiceTest {

    @Autowired
    private TestService testService;

    @Test
    public void createEx() {
        try {
            testService.createEx("createEx");
        } catch (Exception e) {
            log.error("insert error, transaction work, data not inserted");
            Assert.assertNotNull("");
        }
    }

    @Test
    public void transactionNotWork() {
        try {
            int insertAffect = testService.transactionNotWork("transactionNotWork");
            log.info(">>>>> INSERT_AFFECT: [{}]", insertAffect);
        } catch (Exception e) {
            log.error("insert error, transaction not work, data inserted");
            Assert.assertNotNull("");
        }
    }

    @Test
    public void transactionWork() {
        try {
            int insertAffect = testService.transactionWork("transactionWork");
            log.info(">>>>> INSERT_AFFECT: [{}]", insertAffect);
        } catch (Exception e) {
            log.error("insert error, transaction work, data not inserted");
            Assert.assertNotNull("");
        }
    }

}
