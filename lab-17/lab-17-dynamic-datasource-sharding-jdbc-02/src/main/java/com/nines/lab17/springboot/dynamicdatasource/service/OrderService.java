package com.nines.lab17.springboot.dynamicdatasource.service;

import com.nines.lab17.springboot.dynamicdatasource.dataobject.OrderDO;
import com.nines.lab17.springboot.dynamicdatasource.mapper.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author tanyujie
 * @classname OrderService
 * @description TODO
 * @date 2022/9/27 15:28
 * @since 1.0
 */
@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Transactional
    public void add(OrderDO order) {
        // <1.1> 这里先假模假样的读取一下。读取从库
        OrderDO exists = orderMapper.selectById(1);
        System.out.println(exists);

        // <1.2> 插入订单
        orderMapper.insert(order);

        // <1.3> 这里先假模假样的读取一下。读取主库
        // 在 Sharding-JDBC 中，读写分离约定：同一线程且同一数据库连接内，如有写入操作，以后的读操作均从主库读取，用于保证数据一致性。
        exists = orderMapper.selectById(1);
        System.out.println(exists);
    }

    public OrderDO findById(Integer id) {
        return orderMapper.selectById(id);
    }

}
