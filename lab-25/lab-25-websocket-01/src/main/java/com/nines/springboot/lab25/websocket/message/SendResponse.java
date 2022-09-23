package com.nines.springboot.lab25.websocket.message;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanyujie
 * @classname UserJoinNoticeRequest
 * @description 发送消息响应结果
 * @date 2022/9/7 10:51
 * @since 1.0
 */
@Accessors(chain = true)
@Data
public class SendResponse implements Message {

    private static final long serialVersionUID = 1L;

    public static final String TYPE = "SEND_RESPONSE";
    
    /**
     * 消息编号
     */
    private String msgId;
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应提示
     */
    private String message;
}
