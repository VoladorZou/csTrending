package com.example.demo.dgut.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource(value="classpath:redisConfig.properties")
public class RedisConfig {
	@Value(value = "${redis.host}")
	private String host;
	@Value(value = "${redis.port}")
	private int port;
	@Value(value = "${redis.password}")
	private String password;
	@Value(value = "${redis.database}")
	private int database;
	@Value(value = "${redis.maxTotal}")
	private int maxTotal;
	@Value(value = "${redis.maxIdle}")
	private int maxIdle;
	@Value(value = "${redis.minIdle}")
	private int minIdle;
	@Value(value = "${redis.maxWaitMillis}")
	private int maxWaitMillis;
	@Value(value = "${redis.timeout}")
	private int timeout;
	@Value(value = "${redis.testOnBorrow}")
	private boolean testOnBorrow;
}
