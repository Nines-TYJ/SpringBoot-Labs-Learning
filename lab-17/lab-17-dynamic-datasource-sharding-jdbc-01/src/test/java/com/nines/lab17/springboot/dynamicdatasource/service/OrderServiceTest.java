package com.nines.lab17.springboot.dynamicdatasource.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author tanyujie
 * @classname OrderServiceTest
 * @description TODO
 * @date 2022/9/27 14:24
 * @since 1.0
 */
@SpringBootTest
class OrderServiceTest {

    @Resource
    OrderService orderService;

    @Test
    void method01() {
        orderService.method01();
    }

    @Test
    void method02() {
        orderService.method02();
    }

    @Test
    void method03() {
        orderService.method03();
    }

    @Test
    void method04() {
        orderService.method04();
    }

    @Test
    void method05() {
        orderService.method05();
    }
}