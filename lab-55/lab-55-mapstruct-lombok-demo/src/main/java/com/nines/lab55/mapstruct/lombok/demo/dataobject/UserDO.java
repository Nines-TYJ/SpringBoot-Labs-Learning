package com.nines.lab55.mapstruct.lombok.demo.dataobject;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanyujie
 * @classname UserDO
 * @description TODO
 * @date 2022/8/2 15:25
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class UserDO {

    /** 用户编号 **/
    private Integer id;
    /** 用户名 **/
    private String username;
    /** 密码 **/
    private String password;


}
