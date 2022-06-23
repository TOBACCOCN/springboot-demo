package com.springboot.example.redis;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

@ConditionalOnProperty(prefix = "spring.redis.cluster", name = "enable", havingValue = "true")
@Configuration
@ConfigurationProperties(prefix = "spring.redis.cluster")
@Data
public class SimpleRedisClusterConfiguration {

    private String password;
    private String nodes;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @Bean
    public org.springframework.data.redis.connection.RedisClusterConfiguration redisClusterConfiguration() {
       RedisClusterConfiguration configuration = new org.springframework.data.redis.connection.RedisClusterConfiguration();
        configuration.setPassword(password);
        for (String node : nodes.split(",")) {
            configuration.addClusterNode(new RedisNode(node.split(":")[0], Integer.parseInt(node.split(":")[1])));
        }
        return configuration;
    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(org.springframework.data.redis.connection.RedisClusterConfiguration clusterConfig, JedisPoolConfig jedisPoolConfig) {
        return new JedisConnectionFactory(clusterConfig, jedisPoolConfig);
    }

}
