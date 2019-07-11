package com.springboot.example.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JasyptUtilTests {

    private static Logger logger = LoggerFactory.getLogger(JasyptUtilTests.class);

    @Test
    public void encrypt() {
        String content = "test";
        String salt = "test";
        logger.info(">>>>> {}: {}", content, JasyptUtil.encrypt(content, salt));
    }

    @Test
    public void decrypt() {
        String encrypt = "tFKs7gO+mFpEXBqWwAUq0AMBPDMNvdUh";
        String salt = "test";
        logger.info(">>>>> {}: {}", encrypt, JasyptUtil.decrypt(encrypt, salt));
    }

}
