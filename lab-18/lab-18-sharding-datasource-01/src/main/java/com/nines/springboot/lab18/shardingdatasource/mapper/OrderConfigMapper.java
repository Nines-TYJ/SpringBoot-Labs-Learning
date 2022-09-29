package com.nines.springboot.lab18.shardingdatasource.mapper;

import com.nines.springboot.lab18.shardingdatasource.dataobject.OrderConfigDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author tanyujie
 * @classname OrderConfigMapper
 * @description TODO
 * @date 2022/9/28 15:47
 * @since 1.0
 */
@Repository
public interface OrderConfigMapper {

    OrderConfigDO selectById(@Param("id") Integer id);

}
