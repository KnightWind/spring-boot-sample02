package com.fpx.xinyou.conf;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fpx.xinyou.model.User;

@Configuration
@EnableCaching
public class RedisCacheConfig {
  
  @Bean
  public CacheManager cacheManager(
      @SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
    return new RedisCacheManager(redisTemplate);
  }
  
  @Bean
  public RedisTemplate<String, String> redisTemplate(
      RedisConnectionFactory factory) {
    final StringRedisTemplate template = new StringRedisTemplate(factory);
    template.setValueSerializer(new Jackson2JsonRedisSerializer<User>(
        User.class)); //请注意这里

    return template;
  }
}
