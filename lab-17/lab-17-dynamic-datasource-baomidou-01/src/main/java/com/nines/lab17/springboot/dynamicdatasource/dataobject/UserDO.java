package com.nines.lab17.springboot.dynamicdatasource.dataobject;

/**
 * @author tanyujie
 * @classname UserDO
 * @description TODO
 * @date 2022/9/27 11:19
 * @since 1.0
 */
public class UserDO {

    /**
     * 用户编号
     */
    private Integer id;
    /**
     * 账号
     */
    private String username;

    public Integer getId() {
        return id;
    }

    public UserDO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDO setUsername(String username) {
        this.username = username;
        return this;
    }
}
