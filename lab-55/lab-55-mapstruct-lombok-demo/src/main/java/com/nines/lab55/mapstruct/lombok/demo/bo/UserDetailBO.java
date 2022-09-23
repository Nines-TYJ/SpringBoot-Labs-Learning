package com.nines.lab55.mapstruct.lombok.demo.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanyujie
 * @classname UserDetailBO
 * @description TODO
 * @date 2022/8/2 15:42
 * @since 1.0
 */
@Data
@Accessors(chain = true)
public class UserDetailBO {

    private Integer userId;

}
