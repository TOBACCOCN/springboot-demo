package com.springboot.example.https;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * https 配置类
 *
 * @author zhangyonghong
 * @date 2019.7.11
 */
@Configuration
public class HttpsConfiguration {

    @Value("${http.port}")
    private int httpPort;

    @Value("${server.port}")
    private int httpsPort;

    @Bean
    public Connector connector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(httpPort);
        connector.setSecure(false);
        connector.setRedirectPort(httpsPort);
        return connector;
    }

    @Bean
    public TomcatServletWebServerFactory tomcatServletWebServerFactory(Connector connector) {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        // 需要进行 http 端口跳转 https 端口时加上下面一行代码
        // 但是单元测试时（同时启动 springboot 主程序，有时需要主程序配合），
        // 下面这行代码会致使主程序和单元测试同时去监听配置的 http 端口，从而导致单元测试失败
        // tomcat.addAdditionalTomcatConnectors(connector);
        return tomcat;
    }

}
