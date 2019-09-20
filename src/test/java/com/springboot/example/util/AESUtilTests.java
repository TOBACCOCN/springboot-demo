package com.springboot.example.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * AES 加解密工具单元测试
 *
 * @author zhangyonghong
 * @date 2019.6.14
 */
@Slf4j
public class AESUtilTests {

    // private static Logger logger = LoggerFactory.getLogger(AESUtilTests.class);

    @Test
    public void encode() {
        String content = "abc";
        log.info(">>>>> {}--------{}", content, AESUtil.encode(content));
    }

    @Test
    public void decode() {
        String encrypt = "zHFlOpVpD1DT1eL4psjNKg==";
        log.info(">>>>> {}--------{}", encrypt, AESUtil.decode(encrypt));
    }

}
