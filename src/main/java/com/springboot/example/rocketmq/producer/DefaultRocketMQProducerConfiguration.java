package com.springboot.example.rocketmq.producer;

import com.springboot.example.rocketmq.ProducerGroupEnum;
import com.springboot.example.rocketmq.ProducerNameConstant;
import com.springboot.example.rocketmq.RocketMQFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * RocketMQ 生产者配置类
 *
 * @author TOBACCO
 * @date 2020.08.22
 */
@ConditionalOnProperty(prefix = "rocketmq", name = "enable", havingValue = "true")
@Component
@Slf4j
public class DefaultRocketMQProducerConfiguration {

    @Autowired
    private RocketMQFactory rocketMQFactory;

    @Bean(ProducerNameConstant.DEFAULT)
    public DefaultMQProducer defaultMQProducer() {
        return rocketMQFactory.generateProducer(ProducerGroupEnum.GROUP_DEFAULT.toString());
    }

}
