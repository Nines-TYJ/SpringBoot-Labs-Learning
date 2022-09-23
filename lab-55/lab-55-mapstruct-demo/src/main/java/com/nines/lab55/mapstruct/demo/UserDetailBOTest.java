package com.nines.lab55.mapstruct.demo;

import com.nines.lab55.mapstruct.demo.bo.UserBO;
import com.nines.lab55.mapstruct.demo.bo.UserDetailBO;
import com.nines.lab55.mapstruct.demo.convert.UserConvert;
import com.nines.lab55.mapstruct.demo.dataobject.UserDO;

/**
 * @author tanyujie
 * @classname UserDetailBOTest
 * @description TODO
 * @date 2022/8/2 15:45
 * @since 1.0
 */
public class UserDetailBOTest {

    public static void main(String[] args) {
        // 创建userDO对象
        UserDO userDO = new UserDO()
                .setId(1).setUsername("nines").setPassword("nines");
        UserDetailBO userDetailBO = UserConvert.INSTANCE.convertDetail(userDO);

        System.out.println(userDetailBO.getUserId());
    }

}
