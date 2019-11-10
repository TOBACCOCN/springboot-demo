package com.springboot.example.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * Jasypt 配置文件属性加解密工具单元测试
 *
 * @author zhangyonghong
 * @date 2019.6.14
 */
@Slf4j
public class JasyptUtilTests {

    // private static Logger logger = LoggerFactory.getLogger(JasyptUtilTests.class);

    @Test
    public void encrypt() {
        String content = "test";
        String salt = "test";
        log.info(">>>>> [{}]: [{}]", content, JasyptUtil.encrypt(content, salt));
    }

    @Test
    public void decrypt() {
        String encrypt = "tFKs7gO+mFpEXBqWwAUq0AMBPDMNvdUh";
        String salt = "test";
        log.info(">>>>> [{}]: [{}]", encrypt, JasyptUtil.decrypt(encrypt, salt));
    }

}
