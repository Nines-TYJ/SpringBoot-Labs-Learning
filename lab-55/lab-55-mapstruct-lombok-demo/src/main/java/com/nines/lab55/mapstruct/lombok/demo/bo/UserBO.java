package com.nines.lab55.mapstruct.lombok.demo.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanyujie
 * @classname UserBO
 * @description TODO
 * @date 2022/8/2 15:24
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class UserBO {

    /** 用户编号 **/
    private Integer id;
    /** 用户名 **/
    private String username;
    /** 密码 **/
    private String password;

}
