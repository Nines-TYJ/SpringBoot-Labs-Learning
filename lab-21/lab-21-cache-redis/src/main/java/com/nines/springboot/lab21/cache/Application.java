package com.nines.springboot.lab21.cache;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tanyujie
 * @classname Application
 * @description TODO
 * @date 2022/10/12 16:47
 * @since 1.0
 */
@SpringBootApplication
@MapperScan(basePackages = "com.nines.springboot.lab21.cache.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
