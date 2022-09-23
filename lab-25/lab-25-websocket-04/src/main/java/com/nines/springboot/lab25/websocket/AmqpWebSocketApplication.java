package com.nines.springboot.lab25.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tanyujie
 * @classname AmqpWebSocketApplication
 * @description 整合Rabbitmq 实现 多房间
 * @date 2022/9/13 18:01
 * @since 1.0
 */
@SpringBootApplication
public class AmqpWebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmqpWebSocketApplication.class, args);
    }

}
