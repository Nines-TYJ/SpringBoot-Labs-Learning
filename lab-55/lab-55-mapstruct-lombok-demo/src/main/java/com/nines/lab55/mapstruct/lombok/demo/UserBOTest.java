package com.nines.lab55.mapstruct.lombok.demo;


import com.nines.lab55.mapstruct.lombok.demo.bo.UserBO;
import com.nines.lab55.mapstruct.lombok.demo.convert.UserConvert;
import com.nines.lab55.mapstruct.lombok.demo.dataobject.UserDO;

/**
 * @author tanyujie
 * @classname UserBOTest
 * @description TODO
 * @date 2022/8/2 15:30
 * @since 1.0
 */
public class UserBOTest {

    public static void main(String[] args) {
        // 创建userDO对象
        UserDO userDO = new UserDO()
                .setId(1).setUsername("nines").setPassword("nines");
        // 进行转换
        UserBO userBO = UserConvert.INSTANCE.convert(userDO);

        System.out.println(userBO.getId());
        System.out.println(userBO.getUsername());
        System.out.println(userBO.getPassword());
    }

}
