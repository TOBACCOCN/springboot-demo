package com.springboot.example.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnProperty(prefix = "influxdb", name = "enable")
@Configuration
@ConfigurationProperties(prefix = "influxdb")
@Data
public class InfluxDBConfiguration {

	private String url;
	private String token;

	@Bean
	public InfluxDBClient influxDBClient() {
		return InfluxDBClientFactory.create(url, token.toCharArray());
	}

}
