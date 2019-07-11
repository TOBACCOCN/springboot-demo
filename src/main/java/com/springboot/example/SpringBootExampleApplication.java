package com.springboot.example;

import com.alibaba.fastjson.JSONObject;
import com.springboot.example.util.ErrorPrintUtil;
import com.springboot.example.web.websocket.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@SpringBootApplication
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
// @EnableScheduling
public class SpringBootExampleApplication {

    private static Logger logger = LoggerFactory.getLogger(SpringBootExampleApplication.class);

    @Value("${value}")
    private String value;

    private static String staticValue;

    @PostConstruct
    private void setStaticValue() {
        staticValue = this.value;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootExampleApplication.class, args);
        logger.info(">>>>> STATIC_VALUE: {}", staticValue);

        // 给 websocket 长连接客户端发送消息
        new Thread(() -> {
            JSONObject json = new JSONObject();
            json.put("message", "Hello! What's your name?");
            while (true) {
                SessionManager.getSessionMap().forEach((key, session) -> {
                    try {
                        session.getBasicRemote().sendText(json.toString());
                        Thread.sleep(1000*60*3);
                    } catch (Exception e) {
                        ErrorPrintUtil.printErrorMsg(logger, e);
                    }
                });
            }
        }).start();
    }

}
