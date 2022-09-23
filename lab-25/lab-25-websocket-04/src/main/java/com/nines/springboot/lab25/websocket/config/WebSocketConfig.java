package com.nines.springboot.lab25.websocket.config;

import com.nines.springboot.lab25.websocket.constant.WebSocketConstant;
import com.nines.springboot.lab25.websocket.rabbitmq.RabbitmqProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.user.SimpUserRegistry;
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
@EnableConfigurationProperties({RabbitmqProperties.class})
@EnableWebSocketMessageBroker
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final RabbitmqProperties rabbitmqProperties;

    private final UserChannelInterceptor userChannelInterceptor;

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
        // 定义客户端访问服务端消息接口时的前缀
        registry.setApplicationDestinationPrefixes(WebSocketConstant.WS_PREFIX);
        // 定义点对点推送时的前缀为/queue/
        //registry.setUserDestinationPrefix(WebSocketConstant.PREFIX_QUEUE);
        // 配置服务端推送消息给客户端的代理路径 "STOMP broker relay"处理所有消息将消息发送到外部的消息代理
            registry.enableStompBrokerRelay("/temp-queue", "/exchange", "/topic", "/queue", "/amq/queue", "/reply-queue/")
                .setRelayHost(rabbitmqProperties.getHost())
                .setRelayPort(rabbitmqProperties.getStompPort())
                .setClientLogin(rabbitmqProperties.getUsername())
                .setClientPasscode(rabbitmqProperties.getPassword())
                .setSystemLogin(rabbitmqProperties.getUsername())
                .setSystemPasscode(rabbitmqProperties.getPassword())
                .setVirtualHost(rabbitmqProperties.getVirtualHost());
    //    registry. ("/topic");
    }

    /**
     * 定义用户入端通道拦截器
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(userChannelInterceptor);
    }
}
