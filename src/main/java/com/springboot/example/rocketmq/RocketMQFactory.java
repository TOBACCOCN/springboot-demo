package com.springboot.example.rocketmq;

import com.alibaba.fastjson.JSON;
import com.springboot.example.rocketmq.consumer.RocketMQConsumerMsgHandler;
import com.springboot.example.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * RocketMQ 生产者/消费者工厂
 *
 * @author TOBACCO
 * @date 2020.08.22
 */
@ConditionalOnProperty(prefix = "rocketmq", name = "enable", havingValue = "true")
@Component
@Slf4j
public class RocketMQFactory {

    @Autowired
    private RocketMQConfig rocketMQConfig;
    @Autowired
    private SimpleTransactionListener transactionListener;

    public DefaultMQPushConsumer generateConsumer(RocketMQConsumerMsgHandler handler) {
        return generateConsumer(handler, false, false);
    }

    public DefaultMQPushConsumer generateConsumer(RocketMQConsumerMsgHandler handler, boolean isBroadCasting, boolean isOrder) {
        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(rocketMQConfig.getConsumerGroup());
        // Specify name server addresses.
        consumer.setNamesrvAddr(rocketMQConfig.getNameSrvAddr());
        if (isBroadCasting) {
            consumer.setMessageModel(MessageModel.BROADCASTING);
        }

        try {
            // Subscribe one more more topics to consume.
                consumer.subscribe(handler.getTopic(), "*");
            // Register callback to execute on arrival of messages fetched from brokers.
            if (isOrder) {
                consumer.registerMessageListener((MessageListenerOrderly) (msgs, context) -> {
                    // log.info(">>>>> ON_ROCKETMQ_ORDER_MSG: [{}]", msgs);
                    handle(handler, msgs);
                    return ConsumeOrderlyStatus.SUCCESS;
                });
            } else {
                consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
                    log.info(">>>>> ON_ROCKETMQ_MSG: [{}]", msgs);
                    handle(handler, msgs);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                });
            }

            // Launch the consumer instance.
            consumer.start();
            log.info(">>>>> ROCKETMQ_CONSUMER_STARTED");
            return consumer;
        } catch (MQClientException e) {
            ErrorPrintUtil.printErrorMsg(log, e);
            return null;
        }
    }

    /**
     * @param handler 实际的 RocketMQ 消费者消息处理
     * @param msgs    消息内容集合
     */
    private void handle(RocketMQConsumerMsgHandler handler, List<MessageExt> msgs) {
        List<String> messages = new ArrayList<>();
        for (MessageExt messageExt : msgs) {
            messages.add(new String(messageExt.getBody()));
        }
        handler.handle(JSON.toJSONString(messages));
    }

    public DefaultMQProducer generateProducer(String group) {
        // Instantiate with specified producer group name.
        DefaultMQProducer producer = new DefaultMQProducer(group);
        // Specify name server addresses.
        producer.setNamesrvAddr(rocketMQConfig.getNameSrvAddr());

        try {
            // Launch the instance.
            producer.start();

            log.info(">>>>> ROCKETMQ_PRODUCER_STARTED, GROUP: [{}]", group);
            return producer;
        } catch (MQClientException e) {
            ErrorPrintUtil.printErrorMsg(log, e);
            return null;
        }
    }

    public TransactionMQProducer generateTransactionProducer(String group) {
        // Instantiate with specified producer group name.
        TransactionMQProducer producer = new TransactionMQProducer(group);
        // Specify name server addresses.
        producer.setNamesrvAddr(rocketMQConfig.getNameSrvAddr());
        producer.setExecutorService(new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2000)));
        producer.setTransactionListener(transactionListener);

        try {
            // Launch the instance.
            producer.start();

            log.info(">>>>> ROCKETMQ_TRANSACTION_PRODUCER_STARTED, GROUP: [{}]", group);
            return producer;
        } catch (MQClientException e) {
            ErrorPrintUtil.printErrorMsg(log, e);
            return null;
        }
    }

}
