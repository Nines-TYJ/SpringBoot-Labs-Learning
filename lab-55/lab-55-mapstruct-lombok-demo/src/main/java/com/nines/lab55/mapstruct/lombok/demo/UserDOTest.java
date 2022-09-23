package com.nines.lab55.mapstruct.lombok.demo;


import com.nines.lab55.mapstruct.lombok.demo.bo.UserBO;
import com.nines.lab55.mapstruct.lombok.demo.convert.UserConvert;
import com.nines.lab55.mapstruct.lombok.demo.dataobject.UserDO;

/**
 * @author tanyujie
 * @classname UserDOTest
 * @description TODO
 * @date 2022/8/2 15:30
 * @since 1.0
 */
public class UserDOTest {

    public static void main(String[] args) {
        // 创建userDO对象
        UserBO userBO = new UserBO()
                .setId(1).setUsername("nines").setPassword("nines");
        // 进行转换
        UserDO userDO = UserConvert.INSTANCE.convert(userBO);

        System.out.println(userDO.getId());
        System.out.println(userDO.getUsername());
        System.out.println(userDO.getPassword());
    }

}
