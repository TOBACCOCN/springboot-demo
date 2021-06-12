package com.springboot.example.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * kafka 消费者
 *
 * @author zhangyonghong
 * @date 2019.9.18
 */
@ConditionalOnProperty(prefix = "kafka", name = "enable", havingValue = "true")
@Component
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics = "kafka_topic_test")
    public void onMessage(ConsumerRecord<?, ?> record) {
        log.info(">>>>> ON_MESSAGE, TOPIC: [{}], OFFSET: [{}], VALUE: [{}]", record.topic(), record.offset(), record.value());
    }

}
