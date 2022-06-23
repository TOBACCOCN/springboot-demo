package com.springboot.example.rocketmq.consumer;

import com.springboot.example.rocketmq.RocketMQFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * RocketMQ 顺序消息消费者配置类
 *
 * @author TOBACCO
 * @date 2020.08.23
 */
@ConditionalOnProperty(prefix = "rocketmq", name = "enable", havingValue = "true")
// @Configuration
@Slf4j
public class OrderRocketMQConsumerConfiguration {

    @Autowired
    private RocketMQFactory rocketMQFactory;
    @Autowired
    private SimpleRocketMQConsumerMsgHandler simpleRocketMQConsumerMsgHandler;

    @Bean
    public DefaultMQPushConsumer defaultMQPushConsumer() {
        return rocketMQFactory.generateConsumer(simpleRocketMQConsumerMsgHandler, false, true);
    }

}
