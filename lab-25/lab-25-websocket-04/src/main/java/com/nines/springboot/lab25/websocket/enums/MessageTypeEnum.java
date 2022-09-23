package com.nines.springboot.lab25.websocket.enums;

import lombok.Getter;

/**
 * @author tanyujie
 * @classname MessageTypeEnum
 * @description 消息类型枚举
 * @date 2022/9/15 15:44
 * @since 1.0
 */
@Getter
public enum MessageTypeEnum {

    /**
     * 系统消息
     */
    SYSTEM("0", "系统消息"),
    /**
     * 聊天消息
     */
    CHAR("1", "聊天消息");

    private final String code;

    private final String desc;

    MessageTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
