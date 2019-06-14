package com.springboot.example.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MD5UtilTests {

    private static Logger logger = LoggerFactory.getLogger(MD5UtilTests.class);

    @Test
    public void encode() throws NoSuchAlgorithmException {
        logger.info(">>>>> hello: {}", MD5Util.encode("hello".getBytes()));
    }

}
