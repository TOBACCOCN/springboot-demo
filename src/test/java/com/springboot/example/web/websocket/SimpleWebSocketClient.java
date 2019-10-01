package com.springboot.example.web.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springboot.example.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * websocket 客户端
 *
 * @author zhangyonghong
 * @date 2019.7.8
 */
@Slf4j
public class SimpleWebSocketClient extends WebSocketClient {

    // private static Logger logger = LoggerFactory.getLogger(SimpleWebSocketClient.class);

    public SimpleWebSocketClient(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        log.info(">>>>> ON_OPEN");
    }

    @Override
    public void onMessage(String message) {
        log.info(">>>>> ON_MESSAGE: {}", message);

        JSONObject jsonObject = JSON.parseObject(message);
        int code = jsonObject.getIntValue("code");

        int connectSuccess = 307;
        int existFileLength = 406;
        if (code == connectSuccess) {
            ClientSessionManager.connectSuccess(this);
            // 无限循环发送心跳包信息要单独开线程，不然线程在此会被阻塞住
            new Thread(() -> {
                JSONObject heart = new JSONObject();
                int i = 0;
                while (true) {
                    // 心跳包信息，可自行定义
                    heart.put("heart", i++);
                    send(heart.toString());
                    try {
                        TimeUnit.MINUTES.sleep(5);
                        // TimeUnit.SECONDS.sleep(15);
                    } catch (InterruptedException e) {
                        ErrorPrintUtil.printErrorMsg(log, e);
                    }
                }
            }).start();
        } else if (code == existFileLength) {
            ClientSessionManager.setExistFileLength(this, jsonObject.getLong("length"));
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        log.info(">>>>> ON_CLOSE, CODE: {}, REASON: {}, REMOTE: {}", code, reason, reason);
    }

    @Override
    public void onError(Exception e) {
        log.info(">>>>> ON_ERROR");
        ErrorPrintUtil.printErrorMsg(log, e);
    }

}
