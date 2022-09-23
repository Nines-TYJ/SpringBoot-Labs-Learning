package com.nines.springboot.lab25.websocket.message;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanyujie
 * @classname AuthRequest
 * @description 用户认证请求
 * @date 2022/9/7 10:42
 * @since 1.0
 */
@Accessors(chain = true)
@Data
public class AuthRequest implements Message {

    private static final long serialVersionUID = 1L;

    public static final String TYPE = "AUTH_REQUEST";

    /**
     * 认证 Token
     */
    private String accessToken;

}
