package com.springboot.example.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 接口单元测试
 *
 * @author zhangyonghong
 * @date 2022.7.23
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestServiceTest {

    @Autowired
    private TestService testService;

    @Test
    public void createEx() {
        try {
            testService.createEx("createEx");
        } catch (Exception e) {
            log.error("insert error, transaction work, data not inserted");
            Assertions.assertNotNull("");
        }
    }

    @Test
    public void transactionNotWork() {
        try {
            int insertAffect = testService.transactionNotWork("transactionNotWork");
            log.info(">>>>> INSERT_AFFECT: [{}]", insertAffect);
        } catch (Exception e) {
            log.error("insert error, transaction not work, data inserted");
            Assertions.assertNotNull("");
        }
    }

    @Test
    public void transactionWork() {
        try {
            int insertAffect = testService.transactionWork("transactionWork");
            log.info(">>>>> INSERT_AFFECT: [{}]", insertAffect);
        } catch (Exception e) {
            log.error("insert error, transaction work, data not inserted");
            Assertions.assertNotNull("");
        }
    }

}
