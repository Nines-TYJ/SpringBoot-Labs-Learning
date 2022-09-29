package com.nines.springboot.lab18.shardingdatasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tanyujie
 * @classname Application
 * @description TODO
 * @date 2022/9/28 14:58
 * @since 1.0
 */
@SpringBootApplication
@MapperScan(basePackages = "com.nines.springboot.lab18.shardingdatasource.mapper")
public class Application {
}
