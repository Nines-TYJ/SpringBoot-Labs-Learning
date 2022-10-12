package com.nines.springboot.springdatajest.repository;

import com.nines.springboot.springdatajest.Application;
import com.nines.springboot.springdatajest.dataobject.ESProductDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author tanyujie
 * @classname ProductRepositoryTest
 * @description ProductRepositoryTest
 * @date 2022/10/11 10:04
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ProductRepositoryTest {

    @Resource
    ProductRepository productRepository;

    @Test
    public void testInsert() {
        ESProductDO productDO = new ESProductDO()
                .setId(5)
                .setName("HUAWEI PAD")
                .setSellPoint("HUAWEI PAD")
                .setDescription("HuWei iPad")
                .setCid(5)
                .setCategoryName("平板电脑");
        productRepository.save(productDO);
    }

    // 这里要注意，如果使用 save 方法来更新的话，必须是全量字段，否则其它字段会被覆盖。
    // 所以，这里仅仅是作为一个示例。
    @Test // 更新一条记录
    public void testUpdate() {
        ESProductDO product = new ESProductDO();
        product.setId(1);
        product.setCid(2);
        product.setCategoryName("Apple");
        productRepository.save(product);
    }

    // 根据 ID 编号，删除一条记录
    @Test
    public void testDelete() {
        productRepository.deleteById(1);
    }

    // 根据 ID 编号，查询一条记录
    @Test
    public void testSelectById() {
        Optional<ESProductDO> productDO = productRepository.findById(1);
        System.out.println(productDO.isPresent());
    }

    // 根据 ID 编号数组，查询多条记录
    @Test
    public void testSelectByIds() {
        Iterable<ESProductDO> productDOList = productRepository.findAllById(Arrays.asList(1, 4));
        productDOList.forEach(System.out::println);
    }
}