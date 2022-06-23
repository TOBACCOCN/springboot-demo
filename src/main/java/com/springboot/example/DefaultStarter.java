package com.springboot.example;

import com.springboot.example.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.lang.management.ManagementFactory;

/**
 * springboot 程序启动类
 *
 * @author zhangyonghong
 * @date 2019.5.25
 */
@SpringBootApplication
@PropertySource(value = "classpath:application.properties", encoding = "UTF-8")
@EnableCaching
@EnableScheduling
@EnableJms
@Slf4j
public class DefaultStarter {

    // private static Logger logger = LoggerFactory.getLogger(DefaultStarter.class);

    @Value("${value}")
    private String value;

    private static String staticValue;

    @Value("${profile.second}")
    private String secondProfile;

    private static String staticSecondProfile;

    @Autowired
    private User user;

    private static User staticUser;

    @PostConstruct
    private void setStaticValue() {
        staticValue = this.value;
        staticUser = this.user;
        staticSecondProfile = this.secondProfile;
    }

    public static void main(String[] args) {
        SpringApplication.run(DefaultStarter.class, args);
        log.info(">>>>> STATIC_VALUE: [{}]", staticValue);
        log.info(">>>>> STATIC_USER: [{}]", staticUser);
        log.info(">>>>> STATIC_SECOND_PROFILE: [{}]", staticSecondProfile);
        // pid
        log.info(">>>>> PID: [{}]",  ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
    }

}

