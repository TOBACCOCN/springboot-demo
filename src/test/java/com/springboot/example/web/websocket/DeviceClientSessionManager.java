package com.springboot.example.web.websocket;

import com.springboot.example.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DeviceClientSessionManager {

    private static Map<WebSocketClient, Boolean> webSocketClient2ConnectSuccessMap = new ConcurrentHashMap<>();

    public static void connectSuccess(WebSocketClient webSocketClient) {
        synchronized (webSocketClient) {
            webSocketClient2ConnectSuccessMap.put(webSocketClient, true);
            webSocketClient.notify();
        }
    }

    public static Boolean isConnectSuccess(WebSocketClient webSocketClient) {
        synchronized (webSocketClient) {
            try {
                webSocketClient.wait();
            } catch (InterruptedException e) {
                ErrorPrintUtil.printErrorMsg(log, e);
            }
        }
        return webSocketClient2ConnectSuccessMap.get(webSocketClient);
    }

}
