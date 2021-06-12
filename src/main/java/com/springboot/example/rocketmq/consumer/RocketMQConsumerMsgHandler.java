package com.springboot.example.rocketmq.consumer;

/**
 * RocketMQ 消费者消息处理器
 *
 * @author TOBACCO
 * @date 2020.08.22
 */
public interface RocketMQConsumerMsgHandler {

    /**
     * 获取当前消息处理器所属的消费者对应的主题
     *
     * @return 当前消息处理器所属的消费者对应的主题
     */
    String getTopic();

    /**
     * 处理消息
     *
     * @param messages 消息内容
     */
    void handle(String messages);

}
