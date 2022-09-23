package com.nines.springboot.lab25.websocket.controller;

import com.nines.springboot.lab25.websocket.constant.WebSocketConstant;
import com.nines.springboot.lab25.websocket.entity.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * @author tanyujie
 * @classname MsgController
 * @description 消息接口
 * @date 2022/9/13 14:48
 * @since 1.0
 */
@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor
public class MsgController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("/topic")
    @MessageMapping("/topic")
    public void sendAll(@RequestParam String msg) {
        log.info("[发送消息]>>>> msg: {}", msg);
        // 发送消息给客户端
        simpMessagingTemplate.convertAndSend(WebSocketConstant.BROKER_TOPIC, msg);
    }

    @GetMapping("/queue")
    @MessageMapping("/queue")
    public void sendQueue(@RequestBody Message msg) {
        log.info("[接收到消息]>>>> 来自: {}, 内容: {}", msg.getFrom(), msg.getContent());
        /*使用convertAndSendToUser方法，第一个参数为用户id，此时js中的订阅地址为
        "/user/" + 用户Id + "/message",其中"/user"是固定的*/
        simpMessagingTemplate.convertAndSendToUser(msg.getTo(), "/message", msg.getContent());
    }

}
