package com.nines.springboot.lab25.websocket.config;

import com.nines.springboot.lab25.websocket.config.MyPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author tanyujie
 * @classname UserChannelInterceptor
 * @description websocket用户相关渠道拦截
 * @date 2022/9/14 11:02
 * @since 1.0
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class UserChannelInterceptor implements ChannelInterceptor {

    private final SimpUserRegistry simpUserRegistry;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())){
            String username;
            String token;
            List<String> nativeHeader = accessor.getNativeHeader("Authorization");
            if (Objects.isNull(nativeHeader) || nativeHeader.isEmpty()) {
                token = "";
            } else {
                token = nativeHeader.get(0);
            }
            //校验token
            username = token;
            if(StringUtils.isEmpty(username)){
                log.error("token is overtime");
                throw new IllegalStateException("The token is illegal");
            }
            // 判断用户是否已登录
            SimpUser user = simpUserRegistry.getUser(username);
            if (Objects.nonNull(user) && user.hasSessions()) {
                log.error("user is login");
                throw new IllegalStateException("The token is illegal");
            }
            accessor.setUser(new MyPrincipal(username));
            log.info("【{}】用户上线了", username);
        }else if(StompCommand.DISCONNECT.equals(accessor.getCommand())){
            if (Objects.nonNull(accessor.getUser())) {
                log.info("【{}】用户下线了", accessor.getUser().getName());
            }
        }
        return message;
    }
}
