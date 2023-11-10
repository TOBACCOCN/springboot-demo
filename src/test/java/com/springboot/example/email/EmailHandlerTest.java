package com.springboot.example.email;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
@Slf4j
// 工程中开启有 websocket 时，@SpringBootTest 注解需要添加参数 webEnvironment，
// 否者会抛异常：javax.websocket.server.ServerContainer not available
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailHandlerTest {

    // private static Logger logger = LoggerFactory.getLogger(MailServiceTests.class);

    @Autowired
    private EmailHandler emailHandler;
    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void sendSimpleMail() {
        String to = "zyh546391777@163.com";
        String subject = "springboot simple mail";
        String content = "hello this is simple mail";
        emailHandler.sendSimpleMail(to, subject, content);
        Assertions.assertEquals(EmailHandler.SEND_EMAIL_RESULT_SUCCESS, emailHandler.getSendEmailResult());
    }

    @Test
    public void sendHtmlMail() {
        String to = "zyh546391777@163.com";
        String subject = "springboot html mail";
        String content = "<html>\n<body>\n\t<h3>hello world ！ 这是一封 Html 邮件！</h3>\n</body>\n</html>";
        emailHandler.sendHtmlMail(to, subject, content);
        Assertions.assertEquals(EmailHandler.SEND_EMAIL_RESULT_SUCCESS, emailHandler.getSendEmailResult());
    }

    @Test
    public void sendAttachmentsMail() {
        String to = "zyh546391777@163.com";
        String subject = "springboot attachments mail";
        String content = "<html>\n<body>\n\t<h3>hello world ！这是一封带附件的 Html 邮件！</h3>\n</body>\n</html>";
        String filePath = "D:\\download\\AliPayQR.png";
        emailHandler.sendAttachmentsMail(to, subject, content, filePath);
        Assertions.assertEquals(EmailHandler.SEND_EMAIL_RESULT_SUCCESS, emailHandler.getSendEmailResult());
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
        emailHandler.sendInlineResourceMail(to, subject, contentBuilder.toString(), map);
        Assertions.assertEquals(EmailHandler.SEND_EMAIL_RESULT_SUCCESS, emailHandler.getSendEmailResult());
    }

    @Test
    public void sendTemplateMail() {
        String to = "zyh546391777@163.com";
        String subject = "springboot template mail";
        Context context = new Context();
        context.setVariable("id", UUID.randomUUID().toString().replaceAll("-", ""));
        String content = templateEngine.process("mailTemplate", context);
        log.info(">>>>> CONTENT: [{}]", content);
        emailHandler.sendHtmlMail(to, subject, content);
        Assertions.assertEquals(EmailHandler.SEND_EMAIL_RESULT_SUCCESS, emailHandler.getSendEmailResult());
    }

}
