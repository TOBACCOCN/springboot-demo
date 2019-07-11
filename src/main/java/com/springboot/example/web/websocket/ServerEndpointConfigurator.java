package com.springboot.example.web.websocket;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @author  zhangyonghong
 * @date    2019.7.8
 *
 */
public class ServerEndpointConfigurator extends ServerEndpointConfig.Configurator {

    public static String handshakereq = "handshakereq";

    @Override
    public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
        config.getUserProperties().put(handshakereq, request);
    }
}
