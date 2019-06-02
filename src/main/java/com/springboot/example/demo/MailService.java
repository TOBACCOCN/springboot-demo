package com.springboot.example.demo;

public interface MailService {

    void sendSimpleMail(String to, String subject, String content);

    void sendHtmlMail(String to, String subject, String content);

    void sendAttachmentsMail(String to, String subject, String content, String filePath);

    // void sendInlineResourceMail(String to, String subject, String content, Map<String, String> rscIdFilePathMap);
    void sendInlineResourceMail(String to, String subject, String content, String rscId, String res);

}
