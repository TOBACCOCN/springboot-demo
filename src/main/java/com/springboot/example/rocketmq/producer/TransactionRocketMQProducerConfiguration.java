package com.springboot.example.rocketmq.producer;

import com.springboot.example.rocketmq.ProducerGroupEnum;
import com.springboot.example.rocketmq.ProducerNameConstant;
import com.springboot.example.rocketmq.RocketMQFactory;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RocketMQ 事务消息生产者配置类
 *
 * @author TOBACCO
 * @date 2020.08.22
 */
@ConditionalOnProperty(prefix = "rocketmq", name = "enable", havingValue = "true")
@Configuration
public class TransactionRocketMQProducerConfiguration {

    @Autowired
    private RocketMQFactory rocketMQFactory;

    @Bean(ProducerNameConstant.SIMPLE_TRANSACTION)
    public TransactionMQProducer transactionMQProducer() {
        return rocketMQFactory.generateTransactionProducer(ProducerGroupEnum.GROUP_TRANSACTION_SIMPLE.toString());
    }

}
