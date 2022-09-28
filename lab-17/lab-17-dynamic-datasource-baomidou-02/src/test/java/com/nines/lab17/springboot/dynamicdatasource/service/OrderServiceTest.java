package com.nines.lab17.springboot.dynamicdatasource.service;

import com.nines.lab17.springboot.dynamicdatasource.dataobject.OrderDO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tanyujie
 * @classname OrderServiceTest
 * @description TODO
 * @date 2022/9/27 15:28
 * @since 1.0
 */
@SpringBootTest
class OrderServiceTest {

    @Resource
    OrderService orderService;

    @Test
    void add() {
        OrderDO orderDO = new OrderDO();
        orderDO.setUserId(20);
        orderService.add(orderDO);
    }

    @Test
    void findById() {
        OrderDO orderDO = orderService.findById(1);
        System.out.println(orderDO);
    }
}