package com.nines.generator.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tanyujie
 * @classname BaseEntity
 * @description 基础实体
 * @date 2022/10/9 15:20
 * @since 1.0
 */
@Getter
@Setter
@ApiModel(value = "基础对象", description = "")
public class BaseEntity {

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("创建人")
    @TableField("created_by")
    private String createdBy;

    @ApiModelProperty("创建时间")
    @TableField("created_time")
    private String createdTime;

    @ApiModelProperty("更新人")
    @TableField("updated_by")
    private String updatedBy;

    @ApiModelProperty("更新时间")
    @TableField("updated_time")
    private String updatedTime;
}
