package com.springboot.example;

import com.springboot.example.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTests {

    private static Logger logger = LoggerFactory.getLogger(MailTests.class);

    @Autowired
    private MailService mailService;
    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void sendSimpleMail() {
        String to = "zyh546391777@163.com";
        String subject = "springboot simple mail";
        String content = "hello this is simple mail";
        mailService.sendSimpleMail(to, subject, content);
    }

    @Test
    public void sendHtmlMail() {
        String to = "zyh546391777@163.com";
        String subject = "springboot html mail";
        String content = "<html>\n<body>\n\t<h3>hello world ！ 这是一封 Html 邮件！</h3>\n</body>\n</html>";
        mailService.sendHtmlMail(to, subject, content);
    }

    @Test
    public void sendAttachmentsMail() {
        String to = "zyh546391777@163.com";
        String subject = "springboot attachments mail";
        String content = "<html>\n<body>\n\t<h3>hello world ！这是一封带附件的 Html 邮件！</h3>\n</body>\n</html>";
        String filePath = "D:\\download\\AliPayQR.png";
        mailService.sendAttachmentsMail(to, subject, content, filePath);
    }

    @Test
    public void sendInlineResourceMail() {
        String to = "zyh546391777@163.com";
        String subject = "springboot inline resource mail";
        Map<String, String> map = new HashMap<>();
        map.put(UUID.randomUUID().toString().replaceAll("-", ""), "D:\\tunnel\\catherine.png");
        map.put(UUID.randomUUID().toString().replaceAll("-", ""), "D:\\tunnel\\william.png");
        StringBuilder contentBuilder =new StringBuilder();
        contentBuilder.append("<html>\n<body>\n\t<h3>hello world ！这是一封带资源的 Html 邮件！</h3>\n");
        for (String resId: map.keySet()) {
            contentBuilder.append("<img src=\'cid:"+resId+"\'>");
        }
        contentBuilder.append("</body>\n</html>");
        logger.info(">>>>> CONTENT: {}", contentBuilder.toString());
        mailService.sendInlineResourceMail(to, subject, contentBuilder.toString(), map);
    }

    @Test
    public void sendTemplateMail() {
        String to = "zyh546391777@163.com";
        String subject = "springboot template mail";
        Context context = new Context();
        context.setVariable("id", UUID.randomUUID().toString().replaceAll("-", ""));
        String content = templateEngine.process("mailTemplate", context);
        logger.info(">>>>> CONTENT: {}", content);
        mailService.sendHtmlMail(to, subject, content);
    }

}
