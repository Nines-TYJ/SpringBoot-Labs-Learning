package com.nines.lab17.springboot.dynamicdatasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author tanyujie
 * @classname Application
 * @description TODO
 * @date 2022/9/27 10:35
 * @since 1.0
 */
@MapperScan("com.nines.lab17.springboot.dynamicdatasource.mapper")
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("GO GO GO !!!");
    }
}
