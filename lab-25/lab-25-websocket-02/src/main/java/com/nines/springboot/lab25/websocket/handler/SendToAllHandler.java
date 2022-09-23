package com.nines.springboot.lab25.websocket.handler;

import com.nines.springboot.lab25.websocket.message.SendResponse;
import com.nines.springboot.lab25.websocket.message.SendToAllRequest;
import com.nines.springboot.lab25.websocket.message.SendToUserRequest;
import com.nines.springboot.lab25.websocket.util.WebSocketUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;

/**
 * @author tanyujie
 * @classname SendToAllHandler
 * @description 处理 SendToAllRequest 消息
 * @date 2022/9/7 11:07
 * @since 1.0
 */
@Component
public class SendToAllHandler implements MessageHandler<SendToAllRequest> {

    @Override
    public void execute(WebSocketSession session, SendToAllRequest message) {
        // 这里，假装直接成功
        SendResponse sendResponse = new SendResponse().setMsgId(message.getMsgId()).setCode(0);
        WebSocketUtils.send(session, SendResponse.TYPE, sendResponse);
        // 创建转发的消息
        SendToUserRequest sendToUserRequest = new SendToUserRequest().setMsgId(message.getMsgId()).setContent(message.getContent());
        // 广播发送
        WebSocketUtils.broadcast(SendToUserRequest.TYPE, sendToUserRequest);
    }

    @Override
    public String getType() {
        return SendToAllRequest.TYPE;
    }

}
