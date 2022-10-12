package com.nines.springboot.springdataelasticsearch.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author tanyujie
 * @classname ProductConditionBO
 * @description TODO
 * @date 2022/10/11 17:49
 * @since 1.0
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ProductConditionBO {

    /**
     * 商品分类数组
     */
    private List<Category> categories;

    @Getter
    @Setter
    @ToString
    public static class Category {

        /**
         * 分类编号
         */
        private Integer id;
        /**
         * 分类名称
         */
        private String name;

    }

}
