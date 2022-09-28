package com.nines.lab17.springboot.dynamicdatasource.mapper;

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
public interface UserMapper {

    UserDO selectById(@Param("id") Integer id);

}
