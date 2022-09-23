package com.nines.springboot.lab25.websocket.message;

import lombok.Data;

/**
 * @author tanyujie
 * @classname UserJoinNoticeRequest
 * @description 送给指定人的私聊消息
 * @date 2022/9/7 10:51
 * @since 1.0
 */
@Data
public class SendToOneRequest implements Message {

    private static final long serialVersionUID = 1L;

    public static final String TYPE = "SEND_TO_ONE_REQUEST";

    /**
     * 发送给的用户
     */
    private String toUser;
    /**
     * 消息编号
     */
    private String msgId;
    /**
     * 内容
     */
    private String content;
}
