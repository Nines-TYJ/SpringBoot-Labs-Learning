package com.nines.lab17.springboot.dynamicdatasource.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.nines.lab17.springboot.dynamicdatasource.constant.DBConstants;
import com.nines.lab17.springboot.dynamicdatasource.dataobject.OrderDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author tanyujie
 * @classname OrderMapper
 * @description TODO
 * @date 2022/9/27 11:21
 * @since 1.0
 */
@Repository
@DS(DBConstants.DATASOURCE_ORDERS)
public interface OrderMapper {

    OrderDO selectById(@Param("id") Integer id);

}
