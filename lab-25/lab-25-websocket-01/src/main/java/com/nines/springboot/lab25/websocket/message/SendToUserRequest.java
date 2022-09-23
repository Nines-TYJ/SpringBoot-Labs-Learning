package com.nines.springboot.lab25.websocket.message;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanyujie
 * @classname UserJoinNoticeRequest
 * @description 发送消息给一个用户
 * @date 2022/9/7 10:51
 * @since 1.0
 */
@Accessors(chain = true)
@Data
public class SendToUserRequest implements Message {

    private static final long serialVersionUID = 1L;

    public static final String TYPE = "SEND_TO_USER_REQUEST";
    
    /**
     * 消息编号
     */
    private String msgId;
    /**
     * 内容
     */
    private String content;
}
