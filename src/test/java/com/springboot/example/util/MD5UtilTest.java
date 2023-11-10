package com.springboot.example.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

/**
 * MD5 工具单元测试
 *
 * @author zhangyonghong
 * @date 2019.6.14
 */
@Slf4j
public class MD5UtilTest {

    // private static Logger logger = LoggerFactory.getLogger(MD5UtilTests.class);

    @Test
    public void encode() throws NoSuchAlgorithmException {
        log.info(">>>>> hello: [{}]", MD5Util.encode("hello".getBytes()));
    }

}
