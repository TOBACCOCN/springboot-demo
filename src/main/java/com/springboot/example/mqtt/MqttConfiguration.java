package com.springboot.example.mqtt;

import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

/**
 * MQTT 配置类
 * <a href="https://www.emqx.io/docs/zh/v4.4/development/java.html#paho-java-%E4%BD%BF%E7%94%A8%E7%A4%BA%E4%BE%8B">...</a>
 *
 * @author zhangyonghong
 * @date 2021/6/13
 */
@ConditionalOnProperty(prefix = "mqtt", name = "enable", havingValue = "true")
@Configuration
@ConfigurationProperties(prefix = "mqtt")
@Data
public class MqttConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(MqttConfiguration.class);

    private String serverURI;

    private String userName;

    private String password;

    private String topicFilter;

    @Bean
    public MqttClient mqttClient() throws MqttException {
        String clientId = "java_" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
        MqttClient mqttClient = new MqttClient(serverURI, clientId, new MemoryPersistence());

        // MQTT 连接选项
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(userName);
        options.setPassword(password.toCharArray());
        // 保留会话
        options.setCleanSession(true);

        // 设置回调
        mqttClient.setCallback(new DefaultMqttCallBack());

        // 建立连接
        LOG.info(">>>>> MQTT CONNECTING TO SERVER_URI:  [{}]", serverURI);
        mqttClient.connect(options);

        // 订阅，用于测试，通常是作为设备的客户端才会订阅
        mqttClient.subscribe(topicFilter);
        return mqttClient;
    }
}
