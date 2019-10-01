package com.springboot.example.web.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springboot.example.util.ErrorPrintUtil;
import com.springboot.example.util.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * websocket 终结点
 *
 * @author zhangyonghong
 * @date 2019.7.8
 */
@Component
@ServerEndpoint(value = "/websocket", configurator = ServerEndpointConfigurator.class)
@Slf4j
public class SimpleEndpoint {

    // private static Logger logger = LoggerFactory.getLogger(SimpleEndpoint.class);

    // private static Map<String, BinaryHandler> handlerMap = new ConcurrentHashMap<>();
    private static Map<Session, OutputStream> session2OutputStreamMap = new ConcurrentHashMap<>();

    private static final String UPLOAD_DIR = "D:/upload/";
    private static final String CODE = "code";

    private static Boolean allowTimeDiff;
    private static Integer maxTimeDiff;

    @Value("${websocket.allow-time-diff}")
    private void setAllowTimeDiff(Boolean allowTimeDiff) {
        SimpleEndpoint.allowTimeDiff = allowTimeDiff;
        log.info(">>>>> ALLOW_TIME_DIFF: [{}]", allowTimeDiff);
    }

    @Value("${websocket.max-time-diff}")
    private void setMaxTimeDiff(Integer maxTimeDiff) {
        SimpleEndpoint.maxTimeDiff = maxTimeDiff;
        log.info(">>>>> MAX_TIME_DIFF: [{}]", maxTimeDiff);
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws Exception {
        // 根据需要设置空闲超时时间，默认为 0，不会超时
        session.setMaxIdleTimeout(1000 * (60 * 5 + 10));
        log.info(">>>>> TIME_OUT OF SESSION: [{}]", session.getMaxIdleTimeout());
        HandshakeRequest request = (HandshakeRequest) config.getUserProperties().get(ServerEndpointConfigurator.handshakereq);
        Map<String, List<String>> headers = request.getHeaders();
        headers.forEach((key, value) -> log.info(">>>>> HEADER: [{}] = [{}]", key, value));

        Map<String, String> paramMap = getParamMap(headers);
        removeDefaultHeader(paramMap);


        long curtime = Long.parseLong(paramMap.get("curtime"));
        long now = System.currentTimeMillis() / 1000;
        long timeDiff = Math.abs(now - curtime);
        JSONObject json = new JSONObject();

        if (!allowTimeDiff && timeDiff > maxTimeDiff) {
            // curtime 无效，与当前时间秒值相差大于指定的时间差值
            log.info(">>>>> INVALID CURTIME, NOW: [{}], TIME_DIFF: [{}]", now, timeDiff);

            json.put("code", 707);
            json.put("message", "Invalid curtime");
            session.getBasicRemote().sendText(json.toString());
            session.close();
            return;
        }

        // 实际使用时，应该是根据 id 获取 idSecret
        String idSecret = "TEST";
        paramMap.put("idSecret", idSecret);

        if (paramMap.remove("sign").equalsIgnoreCase(SignUtil.generateSignature(paramMap))) {
            json.put(CODE, 307);
            json.put("message", "Connect success");
            session.getBasicRemote().sendText(json.toString());

            // 给 websocket 长连接客户端发送消息
            new Thread(() -> {
                JSONObject message = new JSONObject();
                message.put(CODE, 505);
                message.put("message", "I am websocket server");
                while (true) {
                    try {
                        if (session.isOpen()) {
                            session.getBasicRemote().sendText(message.toString());
                            TimeUnit.MINUTES.sleep(5);
                            // TimeUnit.SECONDS.sleep(15);
                        }
                    } catch (Exception e) {
                        ErrorPrintUtil.printErrorMsg(log, e);
                    }
                }
            }).start();

            // 也可以是其他唯一标识会话用户的字段
            String id = headers.get("id").get(0);
            ServerSessionManager.registerSession(id, session);
            // BinaryHandler binaryHandler = new BinaryHandler();
            // binaryHandler.setId(id);
            // handlerMap.put(session.getId(), binaryHandler);
            // // 处理客户端上传的数据
            // new Thread(binaryHandler).start();
        } else {
            // 检验签名没有通过
            log.info(">>>>> INVALID SIGN");
            json.put(CODE, 407);
            json.put("message", "Invalid sign");
            session.getBasicRemote().sendText(json.toString());
            session.close();
        }
    }

    private void removeDefaultHeader(Map<String, String> paramMap) {
        paramMap.remove("connection");
        paramMap.remove("upgrade");
        paramMap.remove("host");
        paramMap.remove("sec-websocket-key");
        paramMap.remove("sec-websocket-version");
        paramMap.remove("sec-websocket-extensions");
        paramMap.remove("sec-websocket-origin");
    }

    private Map<String, String> getParamMap(Map<String, List<String>> headers) {
        Map<String, String> map = new HashMap<>();
        for (String name : headers.keySet()) {
            List<String> values = headers.get(name);
            StringBuilder builder = new StringBuilder();
            values.forEach(value -> builder.append(value).append(","));
            map.put(name, builder.substring(0, builder.length() - 1));
        }
        return map;
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        log.info(">>>>> ON_MESSAGE: {}", message);

        // session 未注册，说明请求头参数认证未通过，不予处理
        if (ServerSessionManager.getId(session) == null) {
            return;
        }

        try {
            JSONObject jsonObject = JSON.parseObject(message);
            int code = jsonObject.getIntValue(CODE);
            int fileInfoCode = 803;
            int endCode = 304;
            if (code == fileInfoCode) {
                String filename = jsonObject.getString("filename");
                String md5 = jsonObject.getString("md5");
                if (StringUtils.isNotEmpty(filename) && StringUtils.isNotEmpty(md5)) {
                    // BinaryHandler handler = handlerMap.get(session.getId());
                    // handler.setFilename(filename);
                    // handler.setMd5(md5);

                    JSONObject jsonObj = new JSONObject();
                    jsonObj.put(CODE, 406);
                    File file = new File(UPLOAD_DIR, md5 + "-" + filename);
                    jsonObj.put("length", file.length());
                    session.getBasicRemote().sendText(jsonObj.toString());

                    session2OutputStreamMap.put(session, new FileOutputStream(file, true));
                }
            } else if (code == endCode) {
                OutputStream outputStream = session2OutputStreamMap.get(session);
                if (outputStream != null) {
                    // 关闭流
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            log.info(">>>>> MESSAGE IS NOT JSON");
        }
    }

    @OnMessage
    public void onMessage(Session session, ByteBuffer byteBuffer) {
        // log.info(">>>>> ON_MESSAGE, BINARY_LENGTH: {}", byteBuffer.capacity());
        // handlerMap.get(session.getId()).addBinary(byteBuffer);
        OutputStream outputStream = session2OutputStreamMap.get(session);
        if (outputStream != null) {
            try {
                outputStream.write(byteBuffer.array());
            } catch (IOException e) {
                ErrorPrintUtil.printErrorMsg(log, e);
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    ErrorPrintUtil.printErrorMsg(log, ex);
                }
            }
        }
    }

    @OnClose
    public void onClose(Session session) {
        log.info(">>>>> ON_CLOSE");
        ServerSessionManager.unregisterSession(session);
        session2OutputStreamMap.remove(session);
    }

    @OnError
    public void OnError(Session session, Throwable throwable) {
        log.info(">>>>> ON_ERROR");

        ServerSessionManager.unregisterSession(session);
        session2OutputStreamMap.remove(session);

        if (throwable instanceof IOException) {
            log.info(">>>>> IGNORE IT, SESSION CLOSED BY MANUAL");
        } else {
            ErrorPrintUtil.printErrorMsg(log, throwable);
        }
    }

}
