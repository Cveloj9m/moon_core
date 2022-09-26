package com.landingmoon.moon_core;

import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
class MoonCoreApplicationTests {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    void contextLoads() throws InterruptedException {
        redissonClient.getBucket("aaa").set("666", 5L, TimeUnit.SECONDS);

        System.err.println((String) redissonClient.getBucket("aaa").get());

        Thread.sleep(6000);

        System.err.println((String) redissonClient.getBucket("aaa").get());
        System.err.println("结束");
        return;
    }


}
