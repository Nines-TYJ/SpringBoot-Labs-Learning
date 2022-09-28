package com.nines.lab17.springboot.dynamicdatasource.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.nines.lab17.springboot.dynamicdatasource.constant.DBConstants;
import com.nines.lab17.springboot.dynamicdatasource.dataobject.UserDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author tanyujie
 * @classname UserMapper
 * @description TODO
 * @date 2022/9/27 11:23
 * @since 1.0
 */
@Repository
@DS(DBConstants.DATASOURCE_USERS)
public interface UserMapper {

    UserDO selectById(@Param("id") Integer id);

}
