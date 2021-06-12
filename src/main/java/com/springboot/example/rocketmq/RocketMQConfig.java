package com.springboot.example.rocketmq;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * RocketMQ 配置类
 *
 * @author TOBACCO
 * @date 2020.08.22
 */
@ConditionalOnProperty(prefix = "rocketmq", name = "enable", havingValue = "true")
@Configuration
@PropertySource("classpath:rocketmq.properties")
@ConfigurationProperties(prefix = "rocketmq")
@Data
@Slf4j
public class RocketMQConfig {

    private String nameSrvAddr;

    private String consumerGroup;

}
