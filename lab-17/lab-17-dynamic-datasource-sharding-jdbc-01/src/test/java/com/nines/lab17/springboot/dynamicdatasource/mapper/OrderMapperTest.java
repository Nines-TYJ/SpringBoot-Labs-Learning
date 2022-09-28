package com.nines.lab17.springboot.dynamicdatasource.mapper;

import com.nines.lab17.springboot.dynamicdatasource.dataobject.OrderDO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


/**
 * @author tanyujie
 * @classname OrderMapperTest
 * @description TODO
 * @date 2022/9/27 11:29
 * @since 1.0
 */
@SpringBootTest
class OrderMapperTest {

    @Resource
    OrderMapper orderMapper;

    @Test
    void selectById() {
        OrderDO order = orderMapper.selectById(1);
        System.out.println(order);
    }
}