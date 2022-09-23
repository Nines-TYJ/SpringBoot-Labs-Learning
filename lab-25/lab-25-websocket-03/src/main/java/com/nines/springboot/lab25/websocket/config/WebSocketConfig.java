package com.nines.springboot.lab25.websocket.config;

import com.nines.springboot.lab25.websocket.constant.WebSocketConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author tanyujie
 * @classname WebSocketConfig
 * @description websocket 配置类
 * @date 2022/9/13 14:40
 * @since 1.0
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册stomp端点
     *
     * @param registry stomp端点注册对象
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint(WebSocketConstant.WEBSOCKET_PATH)
                .setAllowedOrigins("*")
                .withSockJS();
    }

    /**
     * 配置消息代理
     *
     * @param registry 消息代理注册对象
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 配置服务端推送消息给客户端的代理路径
        registry.enableSimpleBroker(WebSocketConstant.BROKER_QUEUE, WebSocketConstant.BROKER_TOPIC);

        // 定义点对点推送时的前缀为/queue
        registry.setUserDestinationPrefix(WebSocketConstant.BROKER_QUEUE);

        // 定义客户端访问服务端消息接口时的前缀
        registry.setApplicationDestinationPrefixes(WebSocketConstant.WS_PREFIX);
    }
}
