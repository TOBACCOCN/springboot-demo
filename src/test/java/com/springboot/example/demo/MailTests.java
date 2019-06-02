package com.springboot.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTests {

    private static Logger logger = LoggerFactory.getLogger(MailTests.class);

    @Value("${spring.mail.from.addr}")
    private String from;

    @Autowired
    private MailService mailService;

    @Test
    public void testSendSimpleMail() {
        String to = "zyh546391777@163.com";
        String subject = "springboot simple mail";
        String content = "hello this is simple mail";
        mailService.sendSimpleMail(from, to, subject, content);
        logger.info(">>>>> DONE");
    }

}
