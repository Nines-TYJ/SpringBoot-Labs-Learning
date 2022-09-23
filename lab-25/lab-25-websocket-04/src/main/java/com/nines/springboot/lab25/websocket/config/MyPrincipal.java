package com.nines.springboot.lab25.websocket.config;

import java.security.Principal;

/**
 * @author tanyujie
 * @classname MyPrincipal
 * @description websocket-自定义用户
 * @date 2022/9/14 11:06
 * @since 1.0
 */
public class MyPrincipal implements Principal {

    private final String name;

    public MyPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
