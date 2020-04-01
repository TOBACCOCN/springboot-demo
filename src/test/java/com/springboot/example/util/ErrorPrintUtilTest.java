package com.springboot.example.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

/**
 * 栈异常信息输出工具单元测试
 *
 * @author zhangyonghong
 * @date 2019.6.14
 */
@Slf4j
public class ErrorPrintUtilTest {

    // private static Logger logger = LoggerFactory.getLogger(ErrorPrintUtilTests.class);

    @Test
    public void printErrorMsg() {
        try {
            FileInputStream fis = new FileInputStream(new File(""));
            fis.close();
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(log, e);
        }
    }

}
