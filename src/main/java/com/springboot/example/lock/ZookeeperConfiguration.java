package com.springboot.example.lock;

import lombok.Data;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "zookeeper", name = "enable", havingValue = "true")
@Configuration
@Data
public class ZookeeperConfiguration {

    @Value("${zookeeper.connect-string}")
    private String connectString;

    @Bean
    public CuratorFramework curatorFramework() {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(connectString, retryPolicy);
        curatorFramework.start();
        return curatorFramework;
    }
}
