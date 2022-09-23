package com.nines.lab55.mapstruct.demo.convert;

import com.nines.lab55.mapstruct.demo.bo.UserBO;
import com.nines.lab55.mapstruct.demo.bo.UserDetailBO;
import com.nines.lab55.mapstruct.demo.dataobject.UserDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author tanyujie
 * @classname UserConvert
 * @description User 类型转换器
 * @date 2022/8/2 15:27
 * @since 1.0
 */
@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserBO convert(UserDO userDO);

    UserDO convert(UserBO userBO);

    @Mappings({
            @Mapping(source = "id", target = "userId")
    })
    UserDetailBO convertDetail(UserDO userDO);

}
