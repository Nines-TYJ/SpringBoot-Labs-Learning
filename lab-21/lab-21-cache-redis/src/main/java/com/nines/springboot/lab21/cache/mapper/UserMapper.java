package com.nines.springboot.lab21.cache.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nines.springboot.lab21.cache.dataobject.UserDO;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author tanyujie
 * @classname UserMapper
 * @description TODO
 * @date 2022/10/13 9:52
 * @since 1.0
 */
@CacheConfig(cacheNames = "users")
public interface UserMapper extends BaseMapper<UserDO> {

    @Cacheable(value = "eternal_cache", key = "#id")
    UserDO selectById(Long id);

    @CachePut(value = "user_cache", key = "#user.id")
    default UserDO insert0(UserDO user) {
        // 插入记录
        this.insert(user);
        // 返回用户
        return user;
    }

    @CacheEvict(key = "#id")
    int deleteById(Long id);

}
