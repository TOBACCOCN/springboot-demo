package com.springboot.example.web.websocket;

import com.springboot.example.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class DeviceClientSessionManager {

    private static Map<WebSocketClient, Boolean> webSocketClient2ConnectSuccessMap = new ConcurrentHashMap<>();
    private static Map<WebSocketClient, Long> webSocketClient2ExistFileLengthMap = new ConcurrentHashMap<>();

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

    public static void setExistFileLength(WebSocketClient webSocketClient, Long length) {
        synchronized (webSocketClient) {
            webSocketClient2ExistFileLengthMap.put(webSocketClient, length);
            webSocketClient.notify();
        }
    }
    public static Long getExistFileLength(WebSocketClient webSocketClient) {
        synchronized (webSocketClient) {
            try {
                webSocketClient.wait();
            } catch (InterruptedException e) {
                ErrorPrintUtil.printErrorMsg(log, e);
            }
        }
        return  webSocketClient2ExistFileLengthMap.remove(webSocketClient);
    }

}
