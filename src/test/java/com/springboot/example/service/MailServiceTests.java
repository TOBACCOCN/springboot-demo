package com.springboot.example.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 邮件服务接口单元测试
 *
 * @author zhangyonghong
 * @date 2019.6.1
 */
// 工程中开启有 websocket 时，@SpringBootTest 注解需要添加参数 webEnvironment，
// 否者会抛异常：javax.websocket.server.ServerContainer not available
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class MailServiceTests {

    // private static Logger logger = LoggerFactory.getLogger(MailServiceTests.class);

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
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("<html>\n<body>\n\t<h3>hello world ！这是一封带资源的 Html 邮件！</h3>\n");
        for (String resId : map.keySet()) {
            contentBuilder.append("<img src=\'cid:" + resId + "\'>");
        }
        contentBuilder.append("</body>\n</html>");
        log.info(">>>>> CONTENT: [{}]", contentBuilder.toString());
        mailService.sendInlineResourceMail(to, subject, contentBuilder.toString(), map);
    }

    @Test
    public void sendTemplateMail() {
        String to = "zyh546391777@163.com";
        String subject = "springboot template mail";
        Context context = new Context();
        context.setVariable("id", UUID.randomUUID().toString().replaceAll("-", ""));
        String content = templateEngine.process("mailTemplate", context);
        log.info(">>>>> CONTENT: [{}]", content);
        mailService.sendHtmlMail(to, subject, content);
    }

}
