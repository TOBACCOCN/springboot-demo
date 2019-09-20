package com.springboot.example.service;

import java.util.Map;

/**
 * 邮件服务接口
 *
 * @author zhangyonghong
 * @date 2019.6.1
 */
public interface MailService {

    void sendSimpleMail(String to, String subject, String content);

    void sendHtmlMail(String to, String subject, String content);

    void sendAttachmentsMail(String to, String subject, String content, String filePath);

    void sendInlineResourceMail(String to, String subject, String content, Map<String, String> rscIdFilePathMap);

}
