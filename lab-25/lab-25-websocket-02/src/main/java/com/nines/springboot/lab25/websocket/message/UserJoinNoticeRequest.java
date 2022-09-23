package com.nines.springboot.lab25.websocket.message;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanyujie
 * @classname UserJoinNoticeRequest
 * @description 广播用户加入群聊的通知
 * @date 2022/9/7 10:51
 * @since 1.0
 */
@Accessors(chain = true)
@Data
public class UserJoinNoticeRequest implements Message {

    private static final long serialVersionUID = 1L;

    public static final String TYPE = "USER_JOIN_NOTICE_REQUEST";

    private String nickname;

}
