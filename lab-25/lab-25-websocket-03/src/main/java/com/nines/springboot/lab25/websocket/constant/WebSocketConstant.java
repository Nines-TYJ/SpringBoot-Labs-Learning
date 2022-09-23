package com.nines.springboot.lab25.websocket.constant;

/**
 * @author tanyujie
 * @classname WebSocketConstant
 * @description websocket常量类
 * @date 2022/9/13 14:38
 * @since 1.0
 */
public class WebSocketConstant {

    /**
     * stomp端点地址
     */
    public static final String WEBSOCKET_PATH = "/websocket";
    /**
     * websocket前缀
     */
    public static final String WS_PREFIX = "/app";
    /**
     * 点对点消息代理地址
     */
    public static final String BROKER_QUEUE = "/queue/";
    /**
     * 广播消息代理地址
     */
    public static final String BROKER_TOPIC = "/topic";

}
