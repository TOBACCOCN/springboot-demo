package com.springboot.example.kafka;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * kafka 生产者单元测试
 *
 * @author zhangyonghong
 * @date 2019.9.18
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class KafkaProducerTest {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void sendMessage() {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("kafka_topic_test", "KAFKA");
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info(">>>> SEND FAILED");
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                log.info(">>>>> SEND SUCCESS");
            }
        });
    }

}
