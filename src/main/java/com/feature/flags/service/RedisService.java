package com.feature.flags.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void setValue(final String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, 10, TimeUnit.MINUTES);
    }

    public Object getValue(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteKeyFormRedis(String key) {
        redisTemplate.delete(key);
    }
}