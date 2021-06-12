package com.springboot.example.rocketmq.consumer;

import com.springboot.example.rocketmq.TopicEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * RocketMQ 消费者消息处理器
 *
 * @author TOBACCO
 * @date 2020.08.22
 */
@ConditionalOnProperty(prefix = "rocketmq", name = "enable", havingValue = "true")
@Component
@Slf4j
public class DefaultRocketMQConsumerMsgHandler implements RocketMQConsumerMsgHandler {

    @Override
    public String getTopic() {
        return TopicEnum.TOPIC_DEFAULT.toString();
    }

    @Override
    public void handle(String messages) {
        log.info(">>>>> ON_ROCKETMQ_{}_MESSAGE: [{}]", getTopic(), messages);
    }

}
