package com.nines.springboot.springdataelasticsearch.repository;

import com.nines.springboot.springdataelasticsearch.dataobject.ESProductDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author tanyujie
 * @classname ProductRepository
 * @description 第一个泛型设置对应的实体是 ESProductDO ，第二个泛型设置对应的主键类型是 Integer 。
 *              因为实现了 ElasticsearchRepository 接口，Spring Data Jest 会自动生成对应的 CRUD 等等的代码
 * @date 2022/10/11 10:01
 * @since 1.0
 */
public interface ProductRepository02 extends ElasticsearchRepository<ESProductDO, Integer> {

    ESProductDO findByName(String name);

    Page<ESProductDO> findByNameLike(String name, Pageable pageable);

}
