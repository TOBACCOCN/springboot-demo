package com.springboot.example.web.websocket;

import org.apache.commons.lang3.StringUtils;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static Map<String, Session> sessionMap = new ConcurrentHashMap<>();
    private static Map<String, String> idMap = new ConcurrentHashMap<>();

    public static Map<String, Session> getSessionMap() {
        return sessionMap;
    }

    public static void addSession(String id, Session session) {
        sessionMap.put(id, session);
        idMap.put(session.getId(), id);
    }

    public static void removeSession(String id) {
        String sessionId = sessionMap.get(id).getId();
        sessionMap.remove(id);
        idMap.remove(sessionId);
    }

    public static void removeSession(Session session) {
        String id = idMap.get(session.getId());
        if (StringUtils.isNotEmpty(id)) {
            sessionMap.remove(id);
            idMap.remove(session.getId());
        }
    }

}
