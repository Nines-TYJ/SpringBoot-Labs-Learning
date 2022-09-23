package com.nines.springboot.lab47.server.autoconfigure;

import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * @author tanyujie
 * @classname NinesServerAutoConfiguration
 * @description NinesServer 自动装配
 * @date 2022/8/9 11:11
 * @since 1.0
 */

// 声明配置类
@Configuration
// 使 NinesServerProperties 配置属性类生效
@EnableConfigurationProperties(NinesServerProperties.class)
public class NinesServerAutoConfiguration {

    private final Logger logger = LoggerFactory.getLogger(NinesServerAutoConfiguration.class);

    // 声明创建 Bean
    @Bean
    // 需要项目中存在 com.sun.net.httpserver.HttpServer 类。该类为 JDK 自带，所以一定成立。
    @ConditionalOnClass(HttpServer.class)
    public HttpServer httpServer(NinesServerProperties serverProperties) throws IOException {
        // 创建 HttpServer 对象，并启动
        HttpServer server = HttpServer.create(new InetSocketAddress(serverProperties.getPort()), 0);
        server.start();
        logger.info("[httpServer][启动服务器成功，端口为:{}]", serverProperties.getPort());
        // 返回
        return server;
    }

}
