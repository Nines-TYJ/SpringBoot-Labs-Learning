package com.nines.springboot.springdataelasticsearch.repository;

import com.nines.springboot.springdataelasticsearch.dataobject.ESProductDO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.annotation.Resource;

/**
 * @author tanyujie
 * @classname ProductRepository02Test
 * @description TODO
 * @date 2022/10/11 16:36
 * @since 1.0
 */
@SpringBootTest
class ProductRepository02Test {

    @Resource
    ProductRepository02 productRepository02;

    // 根据名字获得一条记录
    @Test
    void findByName() {
        ESProductDO productDO = productRepository02.findByName("MacBook Pro");
        System.out.println(productDO);
    }

    // 使用 name 模糊查询，分页返回结果
    @Test
    void findByNameLike() {
        // 根据情况，是否要制造测试数据
        if (false) {
            testInsert();
        }

        // 创建排序条件
        Sort sort = Sort.by(Sort.Direction.DESC, "id"); // ID 倒序
        // 创建分页条件。
        Pageable pageable = PageRequest.of(0, 10, sort);
        // 执行分页操作
        Page<ESProductDO> page = productRepository02.findByNameLike("MacBook", pageable);
        // 打印
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
    }

    /**
     * 为了给分页制造一点数据
     */
    private void testInsert() {
        for (int i = 1; i <= 100; i++) {
            ESProductDO product = new ESProductDO();
            product.setId(i); // 一般 ES 的 ID 编号，使用 DB 数据对应的编号。这里，先写死
            product.setName("芋道源码：" + i);
            product.setSellPoint("愿半生编码，如一生老友");
            product.setDescription("我只是一个描述");
            product.setCid(1);
            product.setCategoryName("技术");
            productRepository02.save(product);
        }
    }
}