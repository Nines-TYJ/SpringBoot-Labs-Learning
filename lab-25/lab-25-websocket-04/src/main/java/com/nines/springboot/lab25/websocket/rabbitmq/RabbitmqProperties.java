package com.nines.springboot.lab25.websocket.rabbitmq;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tanyujie
 * @classname RabbitmqProperties
 * @description rabbitmq 参数
 * @date 2022/9/14 10:21
 * @since 1.0
 */
@Data
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitmqProperties {

    private String host;

    private Integer port;
    /**
     * Stomp协议端口
     */
    private Integer stompPort;

    private String username;

    private String password;

    private String virtualHost;

}
