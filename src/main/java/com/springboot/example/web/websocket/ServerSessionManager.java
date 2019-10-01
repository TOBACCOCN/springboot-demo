package com.springboot.example.web.websocket;

import org.apache.commons.lang3.StringUtils;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务端长连接会话管理器
 *
 * @author zhangyonghong
 * @date 2019.7.8
 */
public class ServerSessionManager {

    private static Map<String, Session> id2SessionMap = new ConcurrentHashMap<>();
    private static Map<Session, String> session2IdMap = new ConcurrentHashMap<>();

    public static Session getSession(String id) {
        return id2SessionMap.get(id);
    }

    public static String getId(Session session) {
        return session2IdMap.get(session);
    }

    static void registerSession(String id, Session session) {
        id2SessionMap.put(id, session);
        session2IdMap.put(session, id);
    }

    static void unregisterSession(Session session) {
        String id = session2IdMap.get(session);
        if (StringUtils.isNotEmpty(id)) {
            id2SessionMap.remove(id);
        }
        session2IdMap.remove(session);
    }

}
