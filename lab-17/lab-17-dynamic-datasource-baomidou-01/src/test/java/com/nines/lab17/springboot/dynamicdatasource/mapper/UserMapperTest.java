package com.nines.lab17.springboot.dynamicdatasource.mapper;

import com.nines.lab17.springboot.dynamicdatasource.dataobject.UserDO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tanyujie
 * @classname UserMapperTest
 * @description TODO
 * @date 2022/9/27 11:30
 * @since 1.0
 */
@SpringBootTest
class UserMapperTest {

    @Resource
    UserMapper userMapper;

    @Test
    void selectById() {
        UserDO user = userMapper.selectById(1);
        System.out.println(user);
    }
}