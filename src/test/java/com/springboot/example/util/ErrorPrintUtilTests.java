package com.springboot.example.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;

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
