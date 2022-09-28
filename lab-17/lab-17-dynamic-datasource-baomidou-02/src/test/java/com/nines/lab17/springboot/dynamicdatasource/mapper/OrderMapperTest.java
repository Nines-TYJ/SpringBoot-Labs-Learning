package com.nines.lab17.springboot.dynamicdatasource.mapper;

import com.nines.lab17.springboot.dynamicdatasource.dataobject.OrderDO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tanyujie
 * @classname OrderMapperTest
 * @description TODO
 * @date 2022/9/27 15:17
 * @since 1.0
 */
@SpringBootTest
class OrderMapperTest {

    @Resource
    OrderMapper orderMapper;

    @Test
    void selectById() {
        for (int i = 0; i < 10; i++) {
            OrderDO order = orderMapper.selectById(1);
            System.out.println(order);
        }
    }

    @Test
    void insert() {
        OrderDO order = new OrderDO();
        order.setUserId(10);
        orderMapper.insert(order);
    }
}