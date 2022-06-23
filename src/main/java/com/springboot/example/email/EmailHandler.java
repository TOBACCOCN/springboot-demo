package com.springboot.example.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

/**
 * 邮件服务接口实现
 *
 * @author zhangyonghong
 * @date 2019.6.1
 */
@ConditionalOnProperty(prefix = "email", name = "enable", havingValue = "true")
@Component
@Slf4j
public class EmailHandler {

    // private static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.from.addr}")
    private String from;

    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            mailSender.send(message);
            log.info(">>>>> SEND SIMPLE MAIL SUCCESS");
        } catch (MailException e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter, true));
            log.error(stringWriter.toString());
        }
    }

    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            // true 表示需要创建一个 multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(message);
            log.info(">>>>> SEND HTML MAIL SUCCESS");
        } catch (MessagingException e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter, true));
            log.error(stringWriter.toString());
        }
    }

    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);

            mailSender.send(message);
            log.info(">>>>> SEND HTML MAIL WITH ATTACHMENTS SUCCESS");
        } catch (MessagingException e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter, true));
            log.error(stringWriter.toString());
        }
    }

    public void sendInlineResourceMail(String to, String subject, String content, Map<String, String> resIdFilePathMap) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            for (String resId : resIdFilePathMap.keySet()) {
                helper.addInline(resId, new FileSystemResource(new File(resIdFilePathMap.get(resId))));
            }

            mailSender.send(message);
            log.info(">>>>> SEND HTML MAIL WITH INLINE RESOURCE SUCCESS");
        } catch (MessagingException e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter, true));
            log.error(stringWriter.toString());
        }
    }

}
