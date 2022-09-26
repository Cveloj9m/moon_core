package com.landingmoon.moon_core.base.config.redis;

import lombok.AllArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@AllArgsConstructor
public class RedissonConfigure {

    private final RedisProperties redisProperties;

    private final Environment environment;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String nodes = environment.getProperty("spring.redis.cluster.nodes");
        if (!StringUtils.hasText(nodes)) {
            nodes = "redis://" + environment.getProperty("spring.redis.host") + ":" + environment.getProperty("spring.redis.port");
            config.useSingleServer().setAddress(nodes).setPassword(redisProperties.getPassword());
            return Redisson.create(config);
        }
        List<String> list = Arrays.stream(nodes.split(",")).map(x -> "redis://" + x).collect(Collectors.toList());
        config.useClusterServers().setScanInterval(2000).addNodeAddress(list.toArray(new String[list.size()]));
        return Redisson.create(config);
    }

}
