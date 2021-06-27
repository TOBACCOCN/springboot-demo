package com.springboot.example.mqtt;

import io.netty.handler.codec.mqtt.MqttQoS;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * MQTT 消息发布者测试类
 *
 * @author zhangyonghong
 * @date 2021.6.13
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@Slf4j
public class MqttPublisherTest {

    @Autowired
    private MqttClient mqttClient;

    @Test
    public void publish() throws MqttException {
        String payload = "Hello, I'm MQTT client";
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(MqttQoS.EXACTLY_ONCE.value());
        String topic = "default_topic/1";
        mqttClient.publish(topic, message);
    }

}
