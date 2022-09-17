package com.springboot.example.web.websocket;

import com.alibaba.fastjson.JSONObject;
import com.springboot.example.util.ErrorPrintUtil;
import com.springboot.example.util.SignUtil;
import com.springboot.example.util.SimpleX509TrustManager;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.DigestUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * websocket 客户端单元测试
 *
 * @author zhangyonghong
 * @date 2019.7.8
 */
@Slf4j
public class SimpleWebSocketClientTest {

    // private static Logger logger = LoggerFactory.getLogger(SimpleWebSocketClientTests.class);

    private static final String CODE = "code";

    private static WebSocketClient webSocketClient;

    private static final int BUFFER_LENGTH = 1024 * 8;

    @Before
    public void connect() {
        new Thread(() -> {
            try {
                URI uri = new URI("ws://127.0.0.1:9527/websocket");
                // URI uri = new URI("wss://127.0.0.1:9527/websocket");
                Map<String, String> httpHeaders = new HashMap<>();
                // id 一般指客户端标识
                httpHeaders.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
                // httpHeaders.put("curtime", "1234567890");
                httpHeaders.put("curtime", String.valueOf(System.currentTimeMillis() / 1000));
                // idSecret 跟 id 一一对应
                httpHeaders.put("idSecret", "TEST");
                httpHeaders.put("sign", SignUtil.generateSignature(httpHeaders));
                // httpHeaders.put("sign", "sign");
                httpHeaders.remove("idSecret");
                synchronized (SimpleWebSocketClientTest.class) {
                    webSocketClient = new SimpleWebSocketClient(uri, httpHeaders);
                    SimpleWebSocketClientTest.class.notify();
                }
                if (uri.toString().startsWith("wss")) {
                    SSLContext sslContext = SSLContext.getInstance("TLS");
                    TrustManager[] trustManager = {new SimpleX509TrustManager()};
                    sslContext.init(null, trustManager, null);
                    webSocketClient.setSocketFactory(sslContext.getSocketFactory());
                }
                webSocketClient.connect();
            } catch (Exception e) {
                ErrorPrintUtil.printErrorMsg(log, e);
            }
        }).start();
    }

    @Test
    public void sendTextMessage() throws InterruptedException {
        JSONObject message = new JSONObject();
        message.put(CODE, 906);
        message.put("message", "I am websocket client");

        synchronized (SimpleWebSocketClientTest.class) {
            SimpleWebSocketClientTest.class.wait();
        }
        if (webSocketClient == null) {
            log.info(">>>>> WEBSOCKET IS NULL");
            return;
        }

        if (ClientSessionManager.isConnectSuccess(webSocketClient)) {
            webSocketClient.send(message.toString());
        }

        // 让主线程阻塞住不要退出，不然长连接守护线程也会退出
        Thread.currentThread().join();
    }

    @Test
    public void sendBinaryMessage() throws InterruptedException, IOException {
        synchronized (SimpleWebSocketClientTest.class) {
            SimpleWebSocketClientTest.class.wait();
        }
        if (webSocketClient == null) {
            log.info(">>>>> WEBSOCKET IS NULL");
            return;
        }

        if (ClientSessionManager.isConnectSuccess(webSocketClient)) {
            String filePath = "C:\\Users\\TOBACCO\\Desktop\\paypal.png";
            JSONObject fileInfo = new JSONObject();
            fileInfo.put(CODE, 803);
            fileInfo.put("filename", filePath.substring(filePath.lastIndexOf("\\") + 1));
            fileInfo.put("md5", DigestUtils.md5DigestAsHex(new FileInputStream(filePath)));
            webSocketClient.send(fileInfo.toString());

            RandomAccessFile accessFile = new RandomAccessFile(filePath, "r");
            accessFile.seek(ClientSessionManager.getExistFileLength(webSocketClient));
            byte[] buf = new byte[BUFFER_LENGTH];
            int len;
            while ((len = accessFile.read(buf)) != -1) {
                if (len == BUFFER_LENGTH) {
                    webSocketClient.send(buf);
                } else {
                    byte[] temp = new byte[len];
                    System.arraycopy(buf, 0, temp, 0, len);
                    webSocketClient.send(temp);
                }
            }
            accessFile.close();

            JSONObject endMessage = new JSONObject();
            endMessage.put(CODE, 304);
            endMessage.put("end", true);
            webSocketClient.send(endMessage.toString());
        }
        // 让主线程阻塞住不要退出，不然长连接守护线程也会退出
        Thread.currentThread().join();
    }

}
