package com.nines.lab42.testdemo.dao;

import com.nines.lab42.testdemo.dataobject.UserDO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tanyujie
 * @classname UserDaoTest
 * @description TODO
 * @date 2022/8/3 15:52
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class UserDaoTest {

    @Autowired
    UserDao userDao;

    @Test
    @Sql(scripts = "/sql/create_tables.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO t_user(id, username, password)values(1, 'username:1', 'password:1')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/sql/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void selectById() {
        // 查询用户
        UserDO userDO = userDao.selectById(1);
        // 校验数据
        assertEquals(1, userDO.getId(), "编号不一致");
        assertEquals("username:1", userDO.getUsername(), "用户名不一致");
        assertEquals("password:1", userDO.getPassword(), "密码不一致");
    }
}