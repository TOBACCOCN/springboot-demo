package com.springboot.example.web.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * websocket 终结点配置类（允许以注解方式配置暴露 websocket 终结点）
 *
 * @author zhangyonghong
 * @date 2019.7.8
 */
@Component
public class WebSocketConfiguration {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
