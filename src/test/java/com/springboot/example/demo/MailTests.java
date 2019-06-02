package com.springboot.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTests {

    @Autowired
    private MailService mailService;
    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void testSendSimpleMail() {
        String to = "zyh54639177@163.com";
        String subject = "springboot simple mail";
        String content = "hello this is simple mail";
        mailService.sendSimpleMail(to, subject, content);
    }

    @Test
    public void testSendHtmlMail() {
        String to = "zyh546391777@163.com";
        String subject = "springboot html mail";
        String content = "<html>\n" +
                "<body>\n" +
                "\t<h3>hello world ！ 这是一封 Html 邮件！</h3>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendHtmlMail(to, subject, content);
    }

    @Test
    public void testSendAttachmentsMail() {
        String to = "zyh546391777@163.com";
        String subject = "springboot attachments mail";
        String content = "<html>\n" +
                "<body>\n" +
                "\t<h3>hello world ！这是一封带附件的 Html 邮件！</h3>\n" +
                "</body>\n" +
                "</html>";
        String filePath = "D:\\download\\AliPayQR.png";
        mailService.sendAttachmentsMail(to, subject, content, filePath);
    }

    @Test
    public void testSendInlineResourceMail() {
        String to = "zyh546391777@163.com";
        String subject = "springboot inline resource mail";
        String resId = UUID.randomUUID().toString().replaceAll("-", "");
        String content = "<html><body>这是有图片的邮件：<img src=\'cid:" + resId + "\' ></body></html>";
        String res = "D:\\feiq\\日常\\warty-final-ubuntu.png";
        mailService.sendInlineResourceMail(to, subject, content, resId, res);
    }
    @Test
    public void testSendTemplateMail() {
        String to = "zyh546391777@163.com";
        String subject = "springboot template mail";
        Context context = new Context();
        context.setVariable("id", UUID.randomUUID().toString().replaceAll("-", ""));
        String content = templateEngine.process("mailTemplate", context);
        mailService.sendHtmlMail(to, subject, content);
    }

}
