package com.springboot.example.task;

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

    @Scheduled(cron = "0 */5 * * * ?")
    public void process() {
        logger.info(">>>>> SCHEDULER TASK RUNNING: {}", count++);
    }

    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void reportCurrentTime() {
        logger.info(">>>>> NOW IS: {}", LocalDateTime.now().format(dateTimeFormatter));
    }

}
