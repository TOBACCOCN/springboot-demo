package com.springboot.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 配置文件属性加解密单元测试
 *
 * @author zhangyonghong
 * @date 2019.6.1
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class JasyptTests {

    // private static Logger logger = LoggerFactory.getLogger(JasyptTests.class);

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Test
    public void jasypt() {
        log.info(">>>>> REDIS_PASSWORD: {}", redisPassword);
    }

}
