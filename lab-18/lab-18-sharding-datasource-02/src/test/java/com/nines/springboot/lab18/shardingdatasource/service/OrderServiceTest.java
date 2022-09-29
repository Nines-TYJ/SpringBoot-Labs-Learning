package com.nines.springboot.lab18.shardingdatasource.service;

import com.nines.springboot.lab18.shardingdatasource.dataobject.OrderDO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tanyujie
 * @classname OrderServiceTest
 * @description TODO
 * @date 2022/9/28 17:22
 * @since 1.0
 */
@SpringBootTest
class OrderServiceTest {

    @Resource
    OrderService orderService;

    @Test
    void add() {
        OrderDO orderDO = new OrderDO();
        orderDO.setId(2L);
        orderDO.setUserId(22);
        orderService.add(orderDO);
    }

    @Test
    void findById() {
        orderService.findById(2);
    }
}