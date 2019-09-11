package com.springboot.example.web.websocket;

import org.apache.commons.lang3.StringUtils;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static Map<String, Session> id2SessionMap = new ConcurrentHashMap<>();
    private static Map<Session, String> session2IdMap = new ConcurrentHashMap<>();

    public static Map<String, Session> getId2SessionMap() {
        return id2SessionMap;
    }

    static void addSession(String id, Session session) {
        id2SessionMap.put(id, session);
        session2IdMap.put(session, id);
    }

    static void removeSession(String id) {
        Session session = id2SessionMap.get(id);
        if (session != null) {
            session2IdMap.remove(session);
        }
        id2SessionMap.remove(id);
    }

    static void removeSession(Session session) {
        String id = session2IdMap.get(session);
        if (StringUtils.isNotEmpty(id)) {
            id2SessionMap.remove(id);
        }
        session2IdMap.remove(session);
    }

}
