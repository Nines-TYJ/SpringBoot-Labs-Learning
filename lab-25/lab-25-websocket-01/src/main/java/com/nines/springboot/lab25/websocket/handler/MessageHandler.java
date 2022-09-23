package com.nines.springboot.lab25.websocket.handler;

import com.nines.springboot.lab25.websocket.message.Message;

import javax.websocket.Session;

/**
 * @author tanyujie
 * @classname MessageHandler
 * @description 消息处理器接口
 * @date 2022/9/7 11:04
 * @since 1.0
 */
public interface MessageHandler<T extends Message> {

    /**
     * 执行处理消息
     *
     * @param session   会话
     * @param message   消息
     * @author tanyujie
     * @date 2022/9/7 11:05
     * @since v1.0
     */
    void execute(Session session, T message);

    /**
     * 消息类型，即每个 Message 实现类上的 TYPE 静态字段
     *
     * @return java.lang.String
     * @author tanyujie
     * @date 2022/9/7 11:06
     * @since v1.0
     */
    String getType();
}
