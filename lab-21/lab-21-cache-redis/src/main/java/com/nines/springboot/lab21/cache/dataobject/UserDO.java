package com.nines.springboot.lab21.cache.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author tanyujie
 * @classname UserDO
 * @description TODO
 * @date 2022/10/13 9:48
 * @since 1.0
 */
@Getter
@Setter
@ToString
@TableName("users")
public class UserDO implements Serializable {

    /**
     * 用户编号
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 账号
     */
    private String username;
    /**
     * 密码（明文）
     *
     * ps：生产环境下，千万不要明文噢
     */
    private String password;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 是否删除
     */
    @TableLogic
    private Integer deleted;

}
