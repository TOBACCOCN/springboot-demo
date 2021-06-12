package com.springboot.example.activemq;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * ActiveMQ 消息生产者测试类
 *
 * @author zhangyonghong
 * @date 2020.8.21
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class ActiveMQProducerTest {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @Autowired
    private Topic topic;

    @Test
    public void sendQueueMessage() {
        jmsMessagingTemplate.convertAndSend(queue, "QUEUE_MESSAGE");
    }

    @Test
    public void sendTopicMessage() {
        jmsMessagingTemplate.convertAndSend(topic, "TOPIC_MESSAGE");
    }

}
