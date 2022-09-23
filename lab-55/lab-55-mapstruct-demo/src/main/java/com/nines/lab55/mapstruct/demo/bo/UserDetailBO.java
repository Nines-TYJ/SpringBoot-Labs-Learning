package com.nines.lab55.mapstruct.demo.bo;

/**
 * @author tanyujie
 * @classname UserDetailBO
 * @description TODO
 * @date 2022/8/2 15:42
 * @since 1.0
 */
public class UserDetailBO {

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public UserDetailBO setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }
}
