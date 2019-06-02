package com.springboot.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class SchedulerTask {

    private static Logger logger = LoggerFactory.getLogger(SchedulerTask.class);

    private int count;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Scheduled(cron = "*/6 * * * * ?")
    public void process() {
        logger.info(">>>>> this is scheduler task running: {}", count++);
    }

    @Scheduled(fixedRate = 6000)
    public void reportCurrentTime() {
        logger.info(">>>>> now is: {}", LocalDateTime.now().format(dateTimeFormatter));
    }

}
