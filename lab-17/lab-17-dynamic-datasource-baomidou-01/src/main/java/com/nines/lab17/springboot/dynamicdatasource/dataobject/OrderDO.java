package com.nines.lab17.springboot.dynamicdatasource.dataobject;

/**
 * @author tanyujie
 * @classname OrderDO
 * @description TODO
 * @date 2022/9/27 11:18
 * @since 1.0
 */
public class OrderDO {

    /**
     * 订单编号
     */
    private Integer id;
    /**
     * 用户编号
     */
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public OrderDO setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public OrderDO setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }
}
