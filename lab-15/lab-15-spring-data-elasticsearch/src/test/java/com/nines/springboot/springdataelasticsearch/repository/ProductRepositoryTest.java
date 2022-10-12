package com.nines.springboot.springdataelasticsearch.repository;

import com.nines.springboot.springdataelasticsearch.dataobject.ESProductDO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Optional;


/**
 * @author tanyujie
 * @classname ProductRepositoryTest
 * @description ProductRepositoryTest
 * @date 2022/10/11 11:56
 * @since 1.0
 */
@SpringBootTest
class ProductRepositoryTest {

    @Resource
    ProductRepository productRepository;

    @Test
    public void insertTest() {
        ESProductDO productDO = new ESProductDO()
                .setId(108)
                .setName("芋道MacBook Pro")
                .setSellPoint("Apple笔记本电脑MacOS M1处理器")
                .setDescription("MacBook Pro 2021")
                .setCid(105)
                .setCategoryName("笔记本电脑");
        productRepository.save(productDO);
    }

    // 这里要注意，如果使用 save 方法来更新的话，必须是全量字段，否则其它字段会被覆盖。
    // 所以，这里仅仅是作为一个示例。
    @Test // 更新一条记录
    public void updateTest() {
        ESProductDO product = new ESProductDO();
        product.setId(1);
        product.setCid(2);
        product.setCategoryName("Apple");
        productRepository.save(product);
    }

    // 根据 ID 编号，删除一条记录
    @Test
    public void deleteTest() {
        productRepository.deleteById(1);
    }

    // 根据 ID 编号，查询一条记录
    @Test
    public void selectByIdTestS() {
        Optional<ESProductDO> productDO = productRepository.findById(1);
        System.out.println(productDO.isPresent());
    }

    // 根据 ID 编号数组，查询多条记录
    @Test
    public void selectByIdsTest() {
        Iterable<ESProductDO> productDOList = productRepository.findAllById(Arrays.asList(1, 4));
        productDOList.forEach(System.out::println);
    }

}