package com.springboot.example.rocketmq.producer;

import com.springboot.example.rocketmq.TransactionArg;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * RocketMQ 生产者消息发送类
 *
 * @author TOBACCO
 * @date 2020.08.22
 */
public class RocketMQProducerMsgSender {

    /**
     * 发送同步消息
     *
     * @param producer       生产者
     * @param topic          主题
     * @param tags           标签
     * @param message        消息内容
     * @param delayTimeLevel 消息延迟级别（0 表示不延迟，1 到 18分别延迟 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h）
     * @return 发送成功返回 true，否则返回 false
     */
    public static boolean sendSync(DefaultMQProducer producer, String topic, String tags,
                                   String message, int delayTimeLevel) throws Exception {
        // Create a message instance, specifying topic, tag and message body.
        Message msg = new Message(topic, tags, message.getBytes(RemotingHelper.DEFAULT_CHARSET));
        if (delayTimeLevel <= 18 && delayTimeLevel >= 0) {
            msg.setDelayTimeLevel(delayTimeLevel);
        }
        // Call send message to deliver message to one of brokers.
        SendResult sendResult = producer.send(msg);
        return SendStatus.SEND_OK.equals(sendResult.getSendStatus());
    }

    /**
     * 发送异步消息
     *
     * @param producer       生产者
     * @param topic          主题
     * @param tags           标签
     * @param message        消息内容
     * @param delayTimeLevel 消息延迟级别（0 表示不延迟，1 到 18分别延迟 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h）
     * @param sendCallback   发送消息回调
     */
    public static void sendAsync(DefaultMQProducer producer, String topic, String tags, String message,
                                 int delayTimeLevel, SendCallback sendCallback) throws Exception {
        // Create a message instance, specifying topic, tag and message body.
        Message msg = new Message(topic, tags, message.getBytes(RemotingHelper.DEFAULT_CHARSET));
        if (delayTimeLevel <= 18 && delayTimeLevel >= 0) {
            msg.setDelayTimeLevel(delayTimeLevel);
        }
        // Call send message to deliver message to one of brokers.
        producer.send(msg, sendCallback);
    }

    /**
     * 发送不需要回应的消息
     *
     * @param producer       生产者
     * @param topic          主题
     * @param tags           标签
     * @param message        消息内容
     * @param delayTimeLevel 消息延迟级别（0 表示不延迟，1 到 18分别延迟 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h）
     */
    public static void sendOneWay(DefaultMQProducer producer, String topic, String tags,
                                  String message, int delayTimeLevel) throws Exception {
        // Create a message instance, specifying topic, tag and message body.
        Message msg = new Message(topic, tags, message.getBytes(RemotingHelper.DEFAULT_CHARSET));
        if (delayTimeLevel <= 18 && delayTimeLevel >= 0) {
            msg.setDelayTimeLevel(delayTimeLevel);
        }
        // Call send message to deliver message to one of brokers.
        producer.sendOneway(msg);
    }

    /**
     * 发送事务消息
     *
     * @param producer            事务消息生产者
     * @param transactionListener 事务消息监听器
     * @param topic               主题
     * @param tags                标签
     * @param message             消息内容
     */
    public static void sendMessageInTransaction(TransactionMQProducer producer, TransactionListener transactionListener,
                                                String topic, String tags, String message, TransactionArg<Integer> transactionArg) throws Exception {
        // Create a message instance, specifying topic, tag and message body.
        Message msg = new Message(topic, tags, message.getBytes(RemotingHelper.DEFAULT_CHARSET));
        producer.setTransactionListener(transactionListener);
        // Call send message to deliver message to one of brokers.
        producer.sendMessageInTransaction(msg, transactionArg);
    }

    /**
     * 发送顺序消息
     *
     * @param producer 顺序消息生产者
     * @param topic    主题
     * @param tags     标签
     * @param message  消息内容
     * @param id       编号（如订单编号等，用以保证同一编号的多个消息进入到 broker 中的同一个队列）
     */
    public static boolean sendOrderMessage(DefaultMQProducer producer,
                                        String topic, String tags, String message, Long id) throws Exception {
        // Create a message instance, specifying topic, tag and message body.
        Message msg = new Message(topic, tags, message.getBytes(RemotingHelper.DEFAULT_CHARSET));
        // Call send message to deliver message to one of brokers.
        SendResult sendResult = producer.send(msg, (list, m, o) -> list.get((int) ((Long) o % list.size())), id);
        return SendStatus.SEND_OK.equals(sendResult.getSendStatus());
    }

}
