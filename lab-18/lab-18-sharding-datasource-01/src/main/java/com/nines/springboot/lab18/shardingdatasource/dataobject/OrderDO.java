package com.nines.springboot.lab18.shardingdatasource.dataobject;

/**
 * @author tanyujie
 * @classname OrderDO
 * @description TODO
 * @date 2022/9/28 15:43
 * @since 1.0
 */
public class OrderDO {

    /**
     * 订单编号
     */
    private Long id;
    /**
     * 用户编号
     */
    private Integer userId;

    @Override
    public String toString() {
        return "OrderDO{" +
                "id=" + id +
                ", userId=" + userId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public OrderDO setId(Long id) {
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
