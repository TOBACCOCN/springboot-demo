package com.springboot.example.redis;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

@ConditionalOnProperty(prefix = "spring.redis.sentinel", name = "enable", havingValue = "true")
@Configuration
@ConfigurationProperties(prefix = "spring.redis.sentinel")
@Data
public class SimpleRedisSentinelConfiguration {

    private String password;
    private String master;
    private String nodes;

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig();
    }

    @ConditionalOnMissingBean(RedisClusterConfiguration.class)
    @Bean
    public RedisSentinelConfiguration redisSentinelConfiguration() {
        RedisSentinelConfiguration configuration = new RedisSentinelConfiguration();
        configuration.setMaster(master);
        configuration.setPassword(password);
        for (String node : nodes.split(",")) {
            configuration.addSentinel(new RedisNode(node.split(":")[0], Integer.parseInt(node.split(":")[1])));
        }
        return configuration;
    }

    @ConditionalOnMissingBean(RedisClusterConfiguration.class)
    @Bean
    public JedisConnectionFactory jedisConnectionFactory(RedisSentinelConfiguration sentinelConfig, JedisPoolConfig jedisPoolConfig) {
        return new JedisConnectionFactory(sentinelConfig, jedisPoolConfig);
    }

}
