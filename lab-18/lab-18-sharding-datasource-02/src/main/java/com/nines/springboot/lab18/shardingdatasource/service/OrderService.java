package com.nines.springboot.lab18.shardingdatasource.service;

import com.nines.springboot.lab18.shardingdatasource.dataobject.OrderDO;
import com.nines.springboot.lab18.shardingdatasource.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author tanyujie
 * @classname OrderService
 * @description TODO
 * @date 2022/9/28 17:21
 * @since 1.0
 */
@Service
public class OrderService {

    @Resource
    OrderMapper orderMapper;

    @Transactional
    public void add(OrderDO order) {
        // <1.1> 这里先假模假样的读取一下。读取从库
        OrderDO exists = orderMapper.selectById(1);
        System.out.println(exists);

        // <1.2> 插入订单
        orderMapper.insert(order);

        // <1.3> 这里先假模假样的读取一下。读取主库
        // 同一线程且同一数据库连接内，如有写入操作，以后的读操作均从主库读取，用于保证数据一致性。
        exists = orderMapper.selectById(1);
        System.out.println(exists);
    }

    public OrderDO findById(Integer id) {
        return orderMapper.selectById(id);
    }

}
