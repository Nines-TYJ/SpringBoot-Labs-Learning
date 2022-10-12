package com.nines.springboot.springdataelasticsearch.repository;

import com.nines.springboot.springdataelasticsearch.bo.ProductConditionBO;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author tanyujie
 * @classname ProductRepository03Test
 * @description TODO
 * @date 2022/10/11 17:30
 * @since 1.0
 */
@SpringBootTest
class ProductRepository04Test {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    public void test() {
        // 指定索引
        IndexCoordinates index = IndexCoordinates.of("product");
        // <1> 创建 ES 搜索条件
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // <2> 筛选
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.multiMatchQuery("芋道", "name", "sellPoint", "categoryName"));
        nativeSearchQueryBuilder.withQuery(queryBuilder);
        // <3> 聚合
        nativeSearchQueryBuilder.addAggregation(AggregationBuilders.terms("cids").field("cid")); // 商品分类
        // <4> 执行查询
        SearchHits<ProductConditionBO> searchHits = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), ProductConditionBO.class, index);
        // categoryIds 聚合
        Aggregation categoryIdsAggregation = searchHits.getAggregations().get("cids");
        ProductConditionBO condition = new ProductConditionBO();
        if (categoryIdsAggregation != null) {
            condition.setCategories(new ArrayList<>());
            for (Terms.Bucket bucket  : ((Terms) categoryIdsAggregation).getBuckets()) {
                condition.getCategories().add(new ProductConditionBO.Category().setId(bucket.getKeyAsNumber().intValue()));
            }
        }
        // <5> 后续遍历 condition.categories 数组，查询商品分类，设置商品分类名。
        System.out.println(condition);
    }
}