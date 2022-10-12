package com.nines.springboot.springdatajest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;

/**
 * @author tanyujie
 * @classname Application
 * @description 需要排除 ElasticsearchAutoConfiguration 和 ElasticsearchDataAutoConfiguration 自动配置类，否则会自动配置 Spring Data Elasticsearch 。
 * @date 2022/10/11 9:42
 * @since 1.0
 */
@SpringBootApplication(exclude = {ElasticsearchDataAutoConfiguration.class})
public class Application {

}
