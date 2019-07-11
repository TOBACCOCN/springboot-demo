package com.springboot.example.web.websocket;

import com.alibaba.fastjson.JSONObject;
import com.springboot.example.util.ErrorPrintUtil;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Map;

public class SimpleWebSocketClient extends WebSocketClient {

    private static Logger logger = LoggerFactory.getLogger(SimpleWebSocketClient.class);

    public SimpleWebSocketClient(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    @Override
    public void onMessage(String message) {
        logger.info(">>>>> ON_MESSAGE: {}", message);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        // 无限循环发送心跳包信息要单独开线程，不然线程在此会被阻塞住，导致 onMessage 事件方法不会被触发
        new Thread(() -> {
            while (true) {
                JSONObject json = new JSONObject();
                // 心跳包信息，可自行定义
                json.put("message", "HEART BEAT");
                send(json.toString());
                try {
                    Thread.sleep(1000*60*5);
                } catch (InterruptedException e) {
                    ErrorPrintUtil.printErrorMsg(logger, e);
                }
            }
        }).start();
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.info(">>>>> ON_CLOSE, CODE: {}, REASON: {}, REMOTE: {}", code, reason, reason);
    }

    @Override
    public void onError(Exception e) {
        logger.info(">>>>> ON_ERROR");
        ErrorPrintUtil.printErrorMsg(logger, e);
    }

}
