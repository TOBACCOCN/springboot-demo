package com.springboot.example.demo;

public interface MailService {

    void sendSimpleMail(String from, String to, String subject, String content);

}
