package com.springboot.example.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorPrintUtil {

    private static Logger logger = LoggerFactory.getLogger(ErrorPrintUtil.class);

    public static void printErrorMsg(Logger logger, Throwable e) {
        printErrorMsg(logger, e, null);
    }

    public static void printErrorMsg(Logger logger, Throwable e, String prefix) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter, true));
        if (prefix != null) {
            logger.error(prefix + stringWriter.toString());
        } else {
            logger.error(stringWriter.toString());
        }
    }

    @Test
    public void printErrorMsg() {
        try {
            FileInputStream fis = new FileInputStream(new File(""));
            fis.close();
        } catch (Exception e) {
            printErrorMsg(logger, e);
        }
    }

}
