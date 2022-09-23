package com.nines.springboot.lab47.server.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author tanyujie
 * @classname NinesServerProperties
 * @description NinesServer 配置类
 * @date 2022/8/9 11:05
 * @since 1.0
 */
@ConfigurationProperties(prefix = "nines.server")
public class NinesServerProperties {

    /**
     * 默认端口
     */
    private static final Integer DEFAULT_PORT = 8000;

    /**
     * 端口
     */
    private Integer port = DEFAULT_PORT;

    public static Integer getDefaultPort() {
        return DEFAULT_PORT;
    }

    public Integer getPort() {
        return port;
    }

    public NinesServerProperties setPort(Integer port) {
        this.port = port;
        return this;
    }
}
