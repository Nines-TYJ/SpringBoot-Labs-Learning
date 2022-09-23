package com.nines.springboot.lab25.websocket.handler;

import com.nines.springboot.lab25.websocket.message.*;
import com.nines.springboot.lab25.websocket.util.WebSocketUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.Session;

/**
 * @author tanyujie
 * @classname SendToOneHandler
 * @description 处理 SendToOneRequest 消息
 * @date 2022/9/7 11:07
 * @since 1.0
 */
@Component
public class SendToOneHandler implements MessageHandler<SendToOneRequest> {

    @Override
    public void execute(Session session, SendToOneRequest message) {
        // 这里，假装直接成功
        SendResponse sendResponse = new SendResponse().setMsgId(message.getMsgId()).setCode(0);
        WebSocketUtils.send(session, SendResponse.TYPE, sendResponse);
        // 创建转发的消息
        SendToUserRequest sendToUserRequest = new SendToUserRequest().setMsgId(message.getMsgId()).setContent(message.getContent());
        // 广播发送
        WebSocketUtils.send(message.getToUser(), SendToUserRequest.TYPE, sendToUserRequest);
    }

    @Override
    public String getType() {
        return SendToOneRequest.TYPE;
    }

}
