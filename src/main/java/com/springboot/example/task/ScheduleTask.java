package com.springboot.example.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 定时任务类
 *
 * @author zhangyonghong
 * @date 2019.6.1
 */
@Component
@Slf4j
public class ScheduleTask {

    // private static Logger logger = LoggerFactory.getLogger(SchedulerTask.class);

    private int count;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Scheduled(cron = "0 */5 * * * ?")
    public void process() {
        log.info(">>>>> SCHEDULER TASK RUNNING: [{}]", count++);
    }

    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void reportCurrentTime() {
        log.info(">>>>> NOW IS: [{}]", LocalDateTime.now().format(dateTimeFormatter));
    }

}
