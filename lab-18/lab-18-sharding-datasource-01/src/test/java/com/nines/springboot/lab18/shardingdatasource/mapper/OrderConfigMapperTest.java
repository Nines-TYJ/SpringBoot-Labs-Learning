package com.nines.springboot.lab18.shardingdatasource.mapper;

import com.nines.springboot.lab18.shardingdatasource.dataobject.OrderConfigDO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author tanyujie
 * @classname OrderConfigMapperTest
 * @description TODO
 * @date 2022/9/28 15:49
 * @since 1.0
 */
@SpringBootTest
class OrderConfigMapperTest {

    @Resource
    OrderConfigMapper orderConfigMapper;

    @Test
    void selectById() {

        OrderConfigDO orderConfig = orderConfigMapper.selectById(1);
        System.out.println(orderConfig);

    }
}