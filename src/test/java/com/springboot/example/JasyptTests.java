package com.springboot.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JasyptTests {

    private static Logger logger = LoggerFactory.getLogger(JasyptTests.class);

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Test
    public void jasypt() {
        logger.info(">>>>> REDIS_PASSWORD: {}", redisPassword);
    }

}
