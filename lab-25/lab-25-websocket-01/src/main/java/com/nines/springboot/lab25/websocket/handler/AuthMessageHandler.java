package com.nines.springboot.lab25.websocket.handler;

import com.nines.springboot.lab25.websocket.message.AuthRequest;
import com.nines.springboot.lab25.websocket.message.AuthResponse;
import com.nines.springboot.lab25.websocket.message.UserJoinNoticeRequest;
import com.nines.springboot.lab25.websocket.util.WebSocketUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.Session;

/**
 * @author tanyujie
 * @classname AuthMessageHandler
 * @description 处理 AuthRequest 消息
 * @date 2022/9/7 11:07
 * @since 1.0
 */
@Component
public class AuthMessageHandler implements MessageHandler<AuthRequest> {

    @Override
    public void execute(Session session, AuthRequest message) {
        // 如果未传递 accessToken
        if (StringUtils.isEmpty(message.getAccessToken())) {
            WebSocketUtils.send(session, AuthResponse.TYPE, new AuthResponse().setCode(1).setMessage("认证 accessToken 未传入"));
            return;
        }
        // 添加到 WebSocketUtil 中  // 考虑到代码简化，我们先直接使用 accessToken 作为 User
        WebSocketUtils.addSession(session, message.getAccessToken());
        // 判断是否认证成功。这里，假装直接成功
        WebSocketUtils.send(session, AuthResponse.TYPE, new AuthResponse().setCode(0));
        // 通知所有人，某个人加入了。这个是可选逻辑，仅仅是为了演示 // 考虑到代码简化，我们先直接使用 accessToken 作为 User
        WebSocketUtils.broadcast(UserJoinNoticeRequest.TYPE, new UserJoinNoticeRequest().setNickname(message.getAccessToken()));
    }

    @Override
    public String getType() {
        return AuthRequest.TYPE;
    }

}
