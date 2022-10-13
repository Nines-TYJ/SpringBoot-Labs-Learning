package com.nines.springboot.lab21.cache.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author tanyujie
 * @classname RedisServiceTest
 * @description TODO
 * @date 2022/10/13 9:42
 * @since 1.0
 */
@SpringBootTest
class RedisServiceTest {

    @Resource
    RedisService redisService;

    @Test
    public void saveTest() {
        redisService.setCacheObject("test", "redis插入测试");
    }


}