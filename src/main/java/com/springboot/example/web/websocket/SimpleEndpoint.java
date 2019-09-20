package com.springboot.example.web.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springboot.example.util.ErrorPrintUtil;
import com.springboot.example.util.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private static Map<String, BinaryHandler> handlerMap = new ConcurrentHashMap<>();

    private static final String WEBSOCKET_DEFAULT_HEADER_CONNECTION = "connection";
    private static final String WEBSOCKET_DEFAULT_HEADER_UPGRADE = "upgrade";
    private static final String WEBSOCKET_DEFAULT_HEADER_HOST = "host";
    private static final String WEBSOCKET_DEFAULT_HEADER_WEBSOCKET_KEY = "sec-websocket-key";
    private static final String WEBSOCKET_DEFAULT_HEADER_WEBSOCKET_VERSION = "sec-websocket-version";
    private static final String WEBSOCKET_DEFAULT_HEADER_WEBSOCKET_EXTENSIONS = "sec-websocket-extensions";
    private static final String WEBSOCKET_DEFAULT_HEADER_WEBSOCKET_ORIGIN = "sec-websocket-origin";
    private static final String ID = "id";
    private static final String SIGN = "sign";
    private static final String FILENAME = "filename";
    private static final String MD5 = "md5";

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws Exception {
        // 根据需要设置空闲超时时间，默认为 0，不会超时
        session.setMaxIdleTimeout(1000 * (60 * 5 + 10));
        log.info(">>>>> TIME_OUT OF SESSION: {}", session.getMaxIdleTimeout());
        HandshakeRequest request = (HandshakeRequest) config.getUserProperties().get(ServerEndpointConfigurator.handshakereq);
        Map<String, List<String>> headers = request.getHeaders();
        headers.forEach((key, value) -> log.info(">>>>> {}: {}", key, value));

        Map<String, String> paramMap = getParamMap(headers);
        removeDefaultHeader(paramMap);
        if (SignUtil.checkSign(paramMap, SIGN)) {
            // 也可以是其他唯一标识会话用户的字段
            String id = headers.get(ID).get(0);
            SessionManager.addSession(id, session);
            BinaryHandler binaryHandler = new BinaryHandler();
            binaryHandler.setId(id);
            handlerMap.put(session.getId(), binaryHandler);
            // 处理客户端上传的数据
            new Thread(binaryHandler).start();
        } else {
            log.info(">>>>> INVALID SIGN");
            JSONObject json = new JSONObject();
            json.put("message", "invalid sign");
            session.getBasicRemote().sendText(json.toString());
            session.close();
        }
    }

    private void removeDefaultHeader(Map<String, String> paramMap) {
        paramMap.remove(WEBSOCKET_DEFAULT_HEADER_CONNECTION);
        paramMap.remove(WEBSOCKET_DEFAULT_HEADER_UPGRADE);
        paramMap.remove(WEBSOCKET_DEFAULT_HEADER_HOST);
        paramMap.remove(WEBSOCKET_DEFAULT_HEADER_WEBSOCKET_KEY);
        paramMap.remove(WEBSOCKET_DEFAULT_HEADER_WEBSOCKET_VERSION);
        paramMap.remove(WEBSOCKET_DEFAULT_HEADER_WEBSOCKET_EXTENSIONS);
        paramMap.remove(WEBSOCKET_DEFAULT_HEADER_WEBSOCKET_ORIGIN);
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
        try {
            Map map = JSON.parseObject(message, Map.class);
            String filename = (String) map.get(FILENAME);
            String md5 = (String) map.get(MD5);
            if (StringUtils.isNotEmpty(filename) && StringUtils.isNotEmpty(md5)) {
                BinaryHandler handler = handlerMap.get(session.getId());
                handler.setFilename(filename);
                handler.setMd5(md5);
            }
        } catch (Exception e) {
            log.info(">>>>> MESSAGE IS NOT JSON");
        }
    }

    @OnMessage
    public void onMessage(Session session, ByteBuffer byteBuffer) {
        log.info(">>>>> ON_MESSAGE, BINARY_LENGTH: {}", byteBuffer.capacity());
        handlerMap.get(session.getId()).addBinary(byteBuffer);
    }

    @OnClose
    public void onClose(Session session) {
        log.info(">>>>> ON_CLOSE");
        SessionManager.removeSession(session);
    }

    @OnError
    public void OnError(Session session, Throwable throwable) {
        log.info(">>>>> ON_ERROR");
        SessionManager.removeSession(session);
        ErrorPrintUtil.printErrorMsg(log, throwable);
    }

}
