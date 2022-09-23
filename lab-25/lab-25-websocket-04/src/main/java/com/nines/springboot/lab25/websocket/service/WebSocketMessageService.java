package com.nines.springboot.lab25.websocket.service;

import com.alibaba.fastjson.JSONObject;
import com.nines.springboot.lab25.websocket.constant.WebSocketConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author tanyujie
 * @classname WebSocketMessageService
 * @description websocket发送消息
 * @date 2022/9/15 15:47
 * @since 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketMessageService {

    private static final String ALL = "all";

    private final SimpMessagingTemplate simpMessagingTemplate;

    public boolean sendMsg(String msg) {
        try {
            JSONObject msgJson = JSONObject.parseObject(msg);
            if (Objects.equals(msgJson.get("to"), ALL)) {
                simpMessagingTemplate.convertAndSend("/topic/" + msgJson.getString("room"), msgJson);
            } else {
                simpMessagingTemplate.convertAndSend("/queue/" + msgJson.getString("to"), msgJson);
            }
            return true;
        } catch (MessagingException e) {
            log.error("websocket 消息推送失败 ", e);
            return false;
        }
    }

}
