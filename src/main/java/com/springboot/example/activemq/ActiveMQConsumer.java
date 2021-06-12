package com.springboot.example.activemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * ActiveMQ 消息消费者
 *
 * @author zhangyonghong
 * @date 2020.8.21
 */
@ConditionalOnProperty(prefix = "activemq", name = "enable", havingValue = "true")
@Component
@Slf4j
public class ActiveMQConsumer {

    @JmsListener(destination = "${spring.activemq.queue-name}", containerFactory = "queueListener")
    public void onQueueMessage(String message) {
        log.info(">>>>> ON_QUEUE_MESSAGE: [{}]", message);
    }

    @JmsListener(destination = "${spring.activemq.topic-name}", containerFactory = "topicListener")
    public void onTopicMessage(String message) {
        log.info(">>>>> ON_TOPIC_MESSAGE: [{}]", message);
    }

}
