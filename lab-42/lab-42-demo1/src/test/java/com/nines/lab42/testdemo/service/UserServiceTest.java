package com.nines.lab42.testdemo.service;

import com.nines.lab42.testdemo.dao.UserDao;
import com.nines.lab42.testdemo.dataobject.UserDO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tanyujie
 * @classname UserServiceTest
 * @description Service层单元测试
 * @date 2022/8/3 15:16
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @MockBean
    UserDao userDao;

    @Test
    void get() {
        // Mock UserDao 的 selectById 方法
        Mockito.when(userDao.selectById(1)).thenReturn(
                new UserDO().setId(1).setUsername("username:1").setPassword("password:1")
        );
        // 查询用户
        UserDO userDO = userService.get(1);
        // 校验结果
        assertEquals(1, userDO.getId(), "编号不匹配");
        assertEquals("username:1", userDO.getUsername(), "用户名不匹配");
        assertEquals("password:1", userDO.getPassword(), "密码不匹配");
    }
}