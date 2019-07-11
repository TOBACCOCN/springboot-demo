package com.springboot.example.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;

public class MD5UtilTests {

    private static Logger logger = LoggerFactory.getLogger(MD5UtilTests.class);

    @Test
    public void encode() throws NoSuchAlgorithmException {
        logger.info(">>>>> hello: {}", MD5Util.encode("hello".getBytes()));
    }

}
