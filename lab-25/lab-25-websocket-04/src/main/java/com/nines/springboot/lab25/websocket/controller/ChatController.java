package com.nines.springboot.lab25.websocket.controller;

import com.alibaba.fastjson.JSONObject;
import com.nines.springboot.lab25.websocket.constant.WebSocketConstant;
import com.nines.springboot.lab25.websocket.entity.Message;
import com.nines.springboot.lab25.websocket.enums.MessageTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * @author tanyujie
 * @classname ChatController
 * @description 消息接口
 * @date 2022/9/13 14:48
 * @since 1.0
 */
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final RabbitTemplate rabbitTemplate;

    /**
     * 服务端推送给单人的接口
     */
    @ResponseBody
    @GetMapping("/sendToOne")
    public void sendToOne(@RequestParam("uid") String uid, @RequestParam("content") String content) {
        Message message = new Message();
        message.setType(MessageTypeEnum.SYSTEM.getCode());
        message.setContent(content);
        message.setTo(uid);
        message.setFrom("系统消息");
        rabbitTemplate.convertAndSend(WebSocketConstant.WS_TOPIC_EXCHANGE, WebSocketConstant.WS_TOPIC_KEY, JSONObject.toJSONString(message));
    }

    /**
     * 接收 客户端传过来的消息 通过setSender和type 来判别时单发还是群发
     */
    @MessageMapping("/chat.send")
    public void sendMessageTest(@Payload Message message, Principal principal) {
        String name = principal.getName();
        message.setFrom(name);
        rabbitTemplate.convertAndSend(WebSocketConstant.WS_TOPIC_EXCHANGE, WebSocketConstant.WS_TOPIC_KEY, JSONObject.toJSONString(message));
    }

    /**
     * 接收 客户端传过来的消息 上线消息
     */
    @MessageMapping("/chat.online")
    public void addUser(@Payload Message message) {
        log.info("有用户加入到了websocket 消息室" + message.getFrom());
        message.setType(MessageTypeEnum.SYSTEM.getCode());
        message.setContent(message.getFrom() + " 上线~!");
        rabbitTemplate.convertAndSend(WebSocketConstant.WS_TOPIC_EXCHANGE, WebSocketConstant.WS_TOPIC_KEY, JSONObject.toJSONString(message));
    }

}
