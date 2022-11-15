package com.nines.springboot.lab18.shardingdatasource.dataobject;

/**
 * @author tanyujie
 * @classname OrderConfigDO
 * @description TODO
 * @date 2022/9/28 15:44
 * @since 1.0
 */
public class OrderConfigDO {

    /**
     * 编号
     */
    private Integer id;
    /**
     * 支付超时时间
     * 单位：分钟
     */
    private Integer payTimeout;

    @Override
    public String toString() {
        return "OrderConfigDO{" +
                "id=" + id +
                ", payTimeout=" + payTimeout +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public OrderConfigDO setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getPayTimeout() {
        return payTimeout;
    }

    public OrderConfigDO setPayTimeout(Integer payTimeout) {
        this.payTimeout = payTimeout;
        return this;
    }
}
