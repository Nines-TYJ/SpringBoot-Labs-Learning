package com.nines.springboot.lab25.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tanyujie
 * @classname StompWebSocketApplication
 * @description 使用Stomp协议
 * @date 2022/9/8 10:17
 * @since 1.0
 */
@SpringBootApplication
public class StompWebSocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(StompWebSocketApplication.class, args);
    }

}
