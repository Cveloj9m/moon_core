package com.landingmoon.moon_core.base.config.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class RedisLockHelper {

    private final Long timeout = 500L;

    private final String value = UUID.randomUUID().toString();

    private final RedisTemplate redisTemplate;

    public RedisLockHelper(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean tryLock(String key) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
    }

    public Boolean unLock(String key) {
        String script = "if redis.call('get', KEYS[1]) == KEYS[2] then return redis.call('del', KEYS[1]) else return 0 end";
        DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>(script, Long.class);
        return Objects.equals(redisTemplate.execute(defaultRedisScript, Arrays.asList(key, value)), 1L);
    }

}