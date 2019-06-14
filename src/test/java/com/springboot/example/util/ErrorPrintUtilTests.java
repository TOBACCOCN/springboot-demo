package com.springboot.example.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ErrorPrintUtilTests {

    private static Logger logger = LoggerFactory.getLogger(ErrorPrintUtilTests.class);

    @Test
    public void printErrorMsg() {
        try {
            FileInputStream fis = new FileInputStream(new File(""));
            fis.close();
        } catch (Exception e) {
            ErrorPrintUtil.printErrorMsg(logger, e);
        }
    }

}
