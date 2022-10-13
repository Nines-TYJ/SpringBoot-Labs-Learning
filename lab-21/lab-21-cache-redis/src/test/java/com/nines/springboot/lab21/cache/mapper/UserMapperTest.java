package com.nines.springboot.lab21.cache.mapper;

import com.nines.springboot.lab21.cache.dataobject.UserDO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;


/**
 * @author tanyujie
 * @classname UserMapperTest
 * @description TODO
 * @date 2022/10/13 9:59
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Resource
    private CacheManager cacheManager;

    @Test
    public void testCacheManager() {
        System.out.println(cacheManager);
    }

    @Test
    public void testSelectById() {
        // 这里，胖友事先插入一条 id = 1 的记录。
        Long id = 1L;

        // <1.1> 查询 id = 1 的记录
        UserDO user = userMapper.selectById(id);
        System.out.println("user：" + user);
        // <1.2> 判断缓存中，是不是存在
        Assertions.assertNotNull(cacheManager.getCache("eternal_cache").get(user.getId(), UserDO.class), "缓存为空");

        // <2> 查询 id = 1 的记录
        user = userMapper.selectById(id);
        System.out.println("user：" + user);
    }

    @Test
    public void testInsert() {
        // <1> 插入记录
        UserDO user = new UserDO();
        user.setUsername(UUID.randomUUID().toString()); // 随机账号，因为唯一索引
        user.setPassword("nicai");
        user.setCreateTime(LocalDateTime.now());
        user.setDeleted(0);
        userMapper.insert0(user);

        // <2> 判断缓存中，是不是存在
        Assertions.assertNotNull(cacheManager.getCache("user_cache").get(user.getId(), UserDO.class), "缓存为空");
    }

    @Test
    public void testDeleteById() {
        // <1> 插入记录，为了让缓存里有记录
        UserDO user = new UserDO();
        user.setUsername(UUID.randomUUID().toString()); // 随机账号，因为唯一索引
        user.setPassword("nicai");
        user.setCreateTime(LocalDateTime.now());
        user.setDeleted(0);
        userMapper.insert0(user);
        // <2> 判断缓存中，是不是存在
        Assertions.assertNotNull(cacheManager.getCache("user_cache").get(user.getId(), UserDO.class), "缓存为空");

        // <3.1> 删除记录，为了让缓存被删除
        userMapper.deleteById(user.getId());
        // <3.2> 判断缓存中，是不是存在
        Assertions.assertNull(cacheManager.getCache("user_cache").get(user.getId(), UserDO.class), "缓存不为空");
    }

}