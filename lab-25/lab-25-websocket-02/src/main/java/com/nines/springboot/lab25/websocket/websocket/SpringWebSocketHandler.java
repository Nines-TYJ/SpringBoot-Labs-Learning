package com.nines.springboot.lab25.websocket.websocket;

import com.alibaba.fastjson.JSONObject;
import com.nines.springboot.lab25.websocket.handler.MessageHandler;
import com.nines.springboot.lab25.websocket.message.AuthRequest;
import com.nines.springboot.lab25.websocket.message.Message;
import com.nines.springboot.lab25.websocket.util.WebSocketUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author tanyujie
 * @classname SpringWebSocketHandler
 * @description 消息事件处理
 * @date 2022/9/7 16:09
 * @since 1.0
 */
@Slf4j
@SuppressWarnings({"unchecked", "rawtypes"})
public class SpringWebSocketHandler extends TextWebSocketHandler implements InitializingBean {

    /**
     * 消息类型与 MessageHandler 的映射
     */
    private final Map<String, MessageHandler> HANDLERS = new HashMap<>();

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 通过 ApplicationContext 获得所有 MessageHandler Bean
        applicationContext
                // 获得所有 MessageHandler Bean
                .getBeansOfType(MessageHandler.class).values()
                // 添加到 handlers 中
                .forEach(messageHandler -> HANDLERS.put(messageHandler.getType(), messageHandler));
        log.info("[afterPropertiesSet][消息处理器数量：{}]", HANDLERS.size());
    }

    /**
     * 对应 open 事件
     */
    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        log.info("[onOpen][session({}) 接入]", session);
        // <1> 解析 accessToken
        String accessToken = (String) session.getAttributes().get("accessToken");
        // <2> 创建 AuthRequest 消息类型
        AuthRequest authRequest = new AuthRequest().setAccessToken(accessToken);
        // <3> 获得消息处理器
        MessageHandler<AuthRequest> messageHandler = HANDLERS.get(AuthRequest.TYPE);
        if (messageHandler == null) {
            log.error("[onOpen][认证消息类型，不存在消息处理器]");
            return;
        }
        messageHandler.execute(session, authRequest);
    }

    /**
     * 对应 message 事件
     */
    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {
        // 生产环境下，请设置成 debug 级别
        log.info("[onOpen][session({}) 接收到一条消息({})]", session, message);
        try {
            // <1> 获得消息类型
            JSONObject jsonMessage = JSONObject.parseObject(message.getPayload());
            String messageType = jsonMessage.getString("type");
            // <2> 获得消息处理器
            MessageHandler messageHandler = HANDLERS.get(messageType);
            if (messageHandler == null) {
                log.error("[onOpen][认证消息类型，不存在消息处理器]");
                return;
            }
            // <3> 解析消息
            Class<? extends Message> messageClass = this.getMessageClass(messageHandler);
            // <4> 处理消息
            Message messageObj = JSONObject.parseObject(jsonMessage.getString("body"), messageClass);
            messageHandler.execute(session, messageObj);
        } catch (Throwable throwable) {
            log.info("[onMessage][session({}) message({}) 发生异常]", session, throwable);
        }
    }

    /**
     * 对应 close 事件
     */
    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        log.info("[afterConnectionClosed][session({}) 连接关闭。关闭原因是({})}]", session, status);
        WebSocketUtils.removeSession(session);
    }

    /**
     * 对应 error 事件
     */
    @Override
    public void handleTransportError(@NonNull WebSocketSession session, @NonNull Throwable exception) throws Exception {
        log.info("[handleTransportError][session({}) 发生异常]", session, exception);
    }

    /**
     * 通过反射获取 MessageHandler 的泛型类型 Message
     *
     * @param handler	MessageHandler
     * @return java.lang.Class<? extends com.nines.springboot.lab25.websocket.message.Message>
     * @author tanyujie
     * @date 2022/9/7 15:13
     * @since v1.0
     */
    private Class<? extends Message> getMessageClass(MessageHandler handler) {
        // 获得 Bean 对应的 Class 类名。因为有可能被 AOP 代理过。
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(handler);
        // 获得接口的 Type 数组
        Type[] interfaces;
        Class<?> superclass;
        // 此处，是以父类的接口为准
        do {
            interfaces = targetClass.getGenericInterfaces();
            superclass = targetClass.getSuperclass();
        } while (interfaces.length == 0 && Objects.nonNull(superclass));
        // 遍历 interfaces 数组
        for (Type type : interfaces) {
            // 要求 type 是泛型参数
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                // 要求是 MessageHandler 接口
                if (Objects.equals(parameterizedType.getRawType(), MessageHandler.class)) {
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    // 取首个元素
                    if (Objects.nonNull(actualTypeArguments) && actualTypeArguments.length > 0) {
                        return (Class<Message>) actualTypeArguments[0];
                    } else {
                        throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", handler));
                    }
                }
            }
        }
        throw new IllegalStateException(String.format("类型(%s) 获得不到消息类型", handler));
    }
}