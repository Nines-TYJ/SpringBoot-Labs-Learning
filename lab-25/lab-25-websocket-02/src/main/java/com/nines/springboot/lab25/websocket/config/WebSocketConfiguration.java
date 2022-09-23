package com.nines.springboot.lab25.websocket.config;

import com.nines.springboot.lab25.websocket.websocket.SpringWebSocketHandler;
import com.nines.springboot.lab25.websocket.websocket.SpringWebSocketShakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author tanyujie
 * @classname WebSocketConfiguration
 * @description WebSocket配置类
 * @date 2022/9/7 10:24
 * @since 1.0
 */
@Configuration
// 开启 Spring WebSocket
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                // 配置处理器
                .addHandler(this.webSocketHandler(), "/")
                // 配置拦截器
                .addInterceptors(this.webSocketShakeInterceptor())
                // 解决跨域问题
                .setAllowedOrigins("*");
    }

    @Bean
    public SpringWebSocketHandler webSocketHandler() {
        return new SpringWebSocketHandler();
    }

    @Bean
    public SpringWebSocketShakeInterceptor webSocketShakeInterceptor() {
        return new SpringWebSocketShakeInterceptor();
    }

}
