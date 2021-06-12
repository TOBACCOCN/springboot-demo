package com.springboot.example.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * RocketMQ 生产者异步消息发送回调类
 *
 * @author TOBACCO
 * @date 2020.08.22
 */
@ConditionalOnProperty(prefix = "rocketmq", name = "enable", havingValue = "true")
@Component
@Slf4j
public class DefaultRocketMQProducerMsgSendCallback implements SendCallback {

    @Override
    public void onSuccess(SendResult sendResult) {
        log.info(">>>>> DEFAULT_ROCKETMQ_MSG_SEND_SUCCESS");
    }

    @Override
    public void onException(Throwable throwable) {
        log.info(">>>>> DEFAULT_ROCKETMQ_MSG_SEND_FAILED");
    }
}
