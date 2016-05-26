package com.maliavin.vcp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import com.maliavin.vcp.domain.Statistics;

@Configuration
@EnableRedisRepositories("com.maliavin.vcp.repository.statistics")
public class RedisConfig {

    @Bean
    public RedisConnectionFactory connectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("localhost");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setUsePool(true);
        
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<Statistics, String> redisTemplate() {

      RedisTemplate<Statistics, String> template = new RedisTemplate<Statistics, String>();
      template.setConnectionFactory(connectionFactory());
      return template;
    }

}
