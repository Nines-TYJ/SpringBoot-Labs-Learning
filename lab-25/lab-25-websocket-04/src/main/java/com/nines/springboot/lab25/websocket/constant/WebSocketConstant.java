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
    public static final String WEBSOCKET_PATH = "/ws";
    /**
     * websocket前缀
     */
    public static final String WS_PREFIX = "/app";
    /**
     * 点对点消息前缀
     */
    public static final String PREFIX_QUEUE = "/queue/";
    /**
     * 点对点消息代理地址
     */
    public static final String BROKER_QUEUE = "queue";
    /**
     * 广播消息代理地址
     */
    public static final String BROKER_TOPIC = "topic";

    /**
     * rabbitmq 交换机 ws
     */
    public static final String WS_TOPIC_EXCHANGE = "ws.topic.exchange";;
    /**
     * ws topic 队列
     */
    public static final String WS_TOPIC_QUEUE = "wsTopicQueue";
    /**
     * ws topic key
     */
    public static final String WS_TOPIC_KEY = "ws.topic";
}
