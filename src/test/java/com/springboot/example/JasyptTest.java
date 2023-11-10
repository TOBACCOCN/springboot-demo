package com.springboot.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 配置文件属性加解密单元测试
 *
 * @author zhangyonghong
 * @date 2019.6.1
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JasyptTest {

    // private static Logger logger = LoggerFactory.getLogger(JasyptTests.class);

    @Value("${spring.mail.password}")
    private String password;

    @Test
    public void jasypt() {
        log.info(">>>>> password: [{}]", password);
        Assertions.assertNotEquals(null, password);
    }
}
