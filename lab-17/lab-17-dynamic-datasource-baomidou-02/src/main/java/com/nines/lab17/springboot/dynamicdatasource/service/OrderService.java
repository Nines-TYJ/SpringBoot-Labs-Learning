package com.nines.lab17.springboot.dynamicdatasource.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.nines.lab17.springboot.dynamicdatasource.constant.DBConstants;
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
    @DS(DBConstants.DATASOURCE_MASTER)
    public void add(OrderDO order) {
        // 这里先假模假样的读取一下
        OrderDO orderDO = orderMapper.selectById(1);
        System.out.println(orderDO);
        // 插入订单
        orderMapper.insert(order);
    }

    public OrderDO findById(Integer id) {
        return orderMapper.selectById(id);
    }

}
