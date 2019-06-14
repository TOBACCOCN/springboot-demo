package com.springboot.example.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AESUtilTests {

    private static Logger logger = LoggerFactory.getLogger(AESUtilTests.class);

    @Test
    public void encode() {
        String content = "abc";
        logger.info(">>>>> {}--------{}", content, AESUtil.encode(content));
    }

    @Test
    public void decode() {
        String encrypt = "zHFlOpVpD1DT1eL4psjNKg==";
        logger.info(">>>>> {}--------{}", encrypt, AESUtil.decode(encrypt));
    }

}
