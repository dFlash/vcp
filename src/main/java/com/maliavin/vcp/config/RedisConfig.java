package com.maliavin.vcp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories("com.maliavin.vcp.repository.statistics")
public class RedisConfig {

//    @Bean
//    public JedisConnectionFactory connectionFactory() {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.setUsePool(true);
//        jedisConnectionFactory.setHostName("localhost");
//        jedisConnectionFactory.setPort(6379);
//        jedisConnectionFactory.setPassword("");
//        return jedisConnectionFactory;
//    }
//    
//    @Bean 
//    public RedisTemplate<?, ?> redisTemplate()
//    {
//        RedisTemplate<byte[], byte[]> template = new RedisTemplate<byte[], byte[]>();
//        template.setConnectionFactory(connectionFactory());
//        return template;
//    }
    
    @Bean
    public RedisConnectionFactory connectionFactory() {
      return new JedisConnectionFactory();
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {

      RedisTemplate<byte[], byte[]> template = new RedisTemplate<byte[], byte[]>();
      template.setConnectionFactory(connectionFactory());
      return template;
    }

}
