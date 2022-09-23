package com.nines.springboot.lab25.websocket.entity;

import com.nines.springboot.lab25.websocket.enums.MessageTypeEnum;
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
     * 消息类型
     */
    private String type;
    /**
     * 来自（保证唯一）
     */
    private String from;
    /**
     * 去自（保证唯一）
     */
    private String to;
    /**
     * 房间号（保证唯一）
     */
    private String room;
    /**
     * 内容
     */
    private String content;

}
