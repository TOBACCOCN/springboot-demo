package com.springboot.example.rocketmq;

import com.springboot.example.domain.User;
import com.springboot.example.rocketmq.producer.RocketMQProducerMsgSender;
import com.springboot.example.rocketmq.producer.SimpleRocketMQProducerMsgSendCallback;
import com.springboot.example.service.UserService;
import com.springboot.example.util.ErrorPrintUtil;
import com.springboot.example.util.SimpleApplicationContextAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RocketMQ 生产者单元测试
 *
 * @author zhangyonghong
 * @date 2019.9.18
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RocketMQProducerTest {

    @Autowired
    @Qualifier(ProducerNameConstant.SIMPLE)
    private DefaultMQProducer mqProducer;
    @Autowired
    private TransactionMQProducer transactionMQProducer;
    @Autowired
    private TransactionListener transactionListener;
    @Autowired
    private SimpleRocketMQProducerMsgSendCallback simpleRocketMQProducerMsgSendCallback;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void sendSync() throws Exception {
        String topic = TopicEnum.TOPIC_SIMPLE.toString();
        String tags = "TAGS";
        String message = "ROCKETMQ_SYNC_MSG";
        int delayTimeLevel = 3;
        boolean sendResult = RocketMQProducerMsgSender.sendSync(mqProducer, topic, tags, message, delayTimeLevel);
        log.info(">>>>> SEND_SYNC_RESULT:[{}]", sendResult);
    }

    @Test
    public void sendAsync() throws Exception {
        String topic = TopicEnum.TOPIC_SIMPLE.toString();
        String tags = "TAGS";
        String message = "ROCKETMQ_ASYNC_MSG";
        int delayTimeLevel = 3;
        RocketMQProducerMsgSender.sendAsync(mqProducer, topic, tags, message, delayTimeLevel,
                simpleRocketMQProducerMsgSendCallback);
    }

    @Test
    public void sendOneWay() throws Exception {
        String topic = TopicEnum.TOPIC_SIMPLE.toString();
        String tags = "TAGS";
        String message = "ROCKETMQ_ONEWAY_MSG";
        int delayTimeLevel = 3;
        RocketMQProducerMsgSender.sendOneWay(mqProducer, topic, tags, message, delayTimeLevel);
    }

    @Test
    public void sendMessageInTransaction() throws Exception {
        String topic = TopicEnum.TOPIC_SIMPLE.toString();
        String tags = "TAGS";
        String message = "ROCKETMQ_TRANSACTION_MSG";

        TransactionArg<Integer> transactionArg = new TransactionArg<>();
        transactionArg.setCallback(method -> {
            try {
                return (Integer) method.invoke(SimpleApplicationContextAware.getApplicationContext().getBean(UserService.class),
                        new User(10L, "10", 10, "10"));
            } catch (Exception e) {
                ErrorPrintUtil.printErrorMsg(log, e);
            }
            return 0;
        });
        transactionArg.setMethod(UserService.class.getMethod("create", User.class));
        RocketMQProducerMsgSender.sendMessageInTransaction(transactionMQProducer, transactionListener, topic, tags, message, transactionArg);
    }

    @Test
    public void sendOrderMessage() throws Exception {
        String topic = TopicEnum.TOPIC_SIMPLE.toString();
        String tags = "TAGS";
        for (int i = 0; i < 100; ++i) {
            String message = "ROCKETMQ_ORDER_MSG_" + i;
            boolean sendResult = RocketMQProducerMsgSender.sendOrderMessage(mqProducer, topic, tags, message, (long) i % 10);
            log.info(">>>>> SEND_RESULT: [{}]", sendResult);
        }
    }

}
