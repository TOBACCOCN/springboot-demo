package com.springboot.example.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
