package com.nines.springboot.lab25.websocket.message;

import lombok.Data;

/**
 * @author tanyujie
 * @classname UserJoinNoticeRequest
 * @description 发送给所有人的群聊消息
 * @date 2022/9/7 10:51
 * @since 1.0
 */
@Data
public class SendToAllRequest implements Message {

    private static final long serialVersionUID = 1L;

    public static final String TYPE = "SEND_TO_ALL_REQUEST";

    /**
     * 消息编号
     */
    private String msgId;
    /**
     * 内容
     */
    private String content;
}
