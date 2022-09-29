package com.nines.springboot.lab18.shardingdatasource.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nines.springboot.lab18.shardingdatasource.dataobject.OrderDO;
import org.springframework.stereotype.Repository;

/**
 * @author tanyujie
 * @classname OrderMapper
 * @description TODO
 * @date 2022/9/28 15:45
 * @since 1.0
 */
@Repository
public interface OrderMapper extends BaseMapper<OrderDO> {

}
