package com.springboot.example.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * AES 加解密工具单元测试
 *
 * @author zhangyonghong
 * @date 2019.6.14
 */
@Slf4j
public class AESUtilTest {

    // private static Logger logger = LoggerFactory.getLogger(AESUtilTests.class);

    @Test
    public void encrypt() {
        String content = "abc";
        String encrypted = AESUtil.encrypt(content);
        log.info(">>>>> {}--------{}", content, encrypted);
        Assert.assertNotEquals(content, encrypted);
    }

    @Test
    public void decrypt() {
        String encrypted = "zHFlOpVpD1DT1eL4psjNKg==";
        String decrypted = AESUtil.decrypt(encrypted);
        log.info(">>>>> {}--------{}", encrypted, decrypted);
        Assert.assertNotEquals(encrypted, decrypted);
    }

}
