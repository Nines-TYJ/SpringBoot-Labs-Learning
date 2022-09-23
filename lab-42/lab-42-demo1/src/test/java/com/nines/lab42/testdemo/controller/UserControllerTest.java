package com.nines.lab42.testdemo.controller;

import com.nines.lab42.testdemo.dataobject.UserDO;
import com.nines.lab42.testdemo.service.UserService;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tanyujie
 * @classname UserControllerTest
 * @description TODO
 * @date 2022/8/3 15:00
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void get() throws Exception {
        // Mock UserService 的 get 方法
        Mockito.when(userService.get(1)).thenReturn(
                new UserDO().setId(1).setUsername("username:1").setPassword("password:1"));
        // 调用接口查询用户
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/user/get?id=1"));
        // 校验响应状态码
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
        // 校验响应内容方式一：直接全部匹配
        resultActions.andExpect(MockMvcResultMatchers.content().json("{\n" +
                "    \"id\": 1,\n" +
                "    \"username\": \"username:1\",\n" +
                "    \"password\": \"password:1\"\n" +
                "}", true));
        // 校验响应内容方式二：逐个字段匹配
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("id", IsEqual.equalTo(2)));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("username", IsEqual.equalTo("username:1")));
        resultActions.andExpect(MockMvcResultMatchers.jsonPath("password", IsEqual.equalTo("password:1")));
    }
}