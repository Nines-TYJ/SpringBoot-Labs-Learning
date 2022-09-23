package com.nines.springboot.lab25.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author tanyujie
 * @classname WebSocketConfiguration
 * @description WebSocket配置类
 * @date 2022/9/7 10:24
 * @since 1.0
 */
@Configuration
// 无需添加该注解，因为我们并不是使用 Spring WebSocket
// @EnableWebSocket
public class WebSocketConfiguration {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
