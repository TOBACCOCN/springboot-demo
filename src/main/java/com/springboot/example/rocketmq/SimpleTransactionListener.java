package com.springboot.example.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * RocketMQ 生产者事务消息监听器自定义实现
 *
 * @author TOBACCO
 * @date 2020.08.22
 */
@ConditionalOnProperty(prefix = "rocketmq", name = "enable", havingValue = "true")
@Component
@Slf4j
public class SimpleTransactionListener implements TransactionListener {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @SuppressWarnings("unchecked")
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        TransactionArg<Integer> transactionArg = (TransactionArg<Integer>) o;
        Integer result = transactionArg.getCallback().doTransaction(transactionArg.getMethod());
        if (result > 0) {
            return LocalTransactionState.COMMIT_MESSAGE;
        }
        return LocalTransactionState.ROLLBACK_MESSAGE;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        return LocalTransactionState.UNKNOW;
    }

}
