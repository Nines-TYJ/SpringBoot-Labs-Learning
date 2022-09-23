package com.nines.springboot.lab25.websocket.entity;

import lombok.Data;

/**
 * @author tanyujie
 * @classname Message
 * @description 消息类
 * @date 2022/9/13 15:13
 * @since 1.0
 */
@Data
public class Message {

    /**
     * 消息编码
     */
    private String code;
    /**
     * 来自（保证唯一）
     */
    private String from;
    /**
     * 去自（保证唯一）
     */
    private String to;
    /**
     * 内容
     */
    private String content;

}
