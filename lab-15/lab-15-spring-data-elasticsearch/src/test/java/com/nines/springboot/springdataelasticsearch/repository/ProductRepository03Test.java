package com.nines.springboot.springdataelasticsearch.repository;

import com.nines.springboot.springdataelasticsearch.dataobject.ESProductDO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.annotation.Resource;

/**
 * @author tanyujie
 * @classname ProductRepository03Test
 * @description TODO
 * @date 2022/10/11 17:30
 * @since 1.0
 */
@SpringBootTest
class ProductRepository03Test {

    @Resource
    private ProductRepository03 productRepository03;

    @Test
    public void search() {
        // 查找分类为 1 + 指定关键字，并且按照 id 升序
        Page<ESProductDO> page = productRepository03.search(1, "技术",
                PageRequest.of(0, 5, Sort.Direction.ASC, "id"));
        System.out.println(page.getTotalPages());

        // 查找分类为 1 ，并且按照 id 升序
        page = productRepository03.search(1, null,
                PageRequest.of(0, 5, Sort.Direction.ASC, "id"));
        System.out.println(page.getTotalPages());

        // 查找分类为 1 ，并且按照 id 升序
                page = productRepository03.search(null, "MacOS",
                PageRequest.of(0, 5, Sort.Direction.DESC, "id"));
        System.out.println(page.getTotalPages());
        page.get().forEach(System.out::println);
    }
}