package com.springboot.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
@EnableScheduling
public class SpringBootDemoApplication {

    private static Logger logger = LoggerFactory.getLogger(SpringBootDemoApplication.class);

    @Value("${value}")
    private String value;

    private static String staticValue;

    @PostConstruct
    private void setStaticValue() {
        staticValue = this.value;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
        logger.info(">>>>> STATIC_VALUE: {}", staticValue);
    }

}
