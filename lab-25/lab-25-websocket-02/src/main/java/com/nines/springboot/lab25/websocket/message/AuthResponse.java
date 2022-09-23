package com.nines.springboot.lab25.websocket.message;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tanyujie
 * @classname AuthResponse
 * @description 用户认证响应
 * @date 2022/9/7 10:47
 * @since 1.0
 */
@Accessors(chain = true)
@Data
public class AuthResponse implements Message {

    private static final long serialVersionUID = 1L;

    public static final String TYPE = "AUTH_RESPONSE";

    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应提示
     */
    private String message;

}
