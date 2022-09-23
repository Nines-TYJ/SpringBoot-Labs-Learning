package com.nines.springboot.lab25.websocket.rabbitmq;

import com.nines.springboot.lab25.websocket.constant.WebSocketConstant;
import com.nines.springboot.lab25.websocket.service.WebSocketMessageService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tanyujie
 * @classname RabbitmqConfig
 * @description rabbitmq配置类
 * @date 2022/9/14 11:29
 * @since 1.0
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({RabbitmqProperties.class})
public class RabbitmqConfig {

    private final RabbitmqProperties rabbitmqProperties;

    private final WebSocketMessageService webSocketMessageService;

    /**
     * websocket 交换机
     *
     * @return org.springframework.amqp.core.DirectExchange
     * @author tanyujie
     * @date 2022/9/14 11:52
     * @since v1.0
     */
    @Bean
    public TopicExchange wsTopicExchange() {
        return new TopicExchange(WebSocketConstant.WS_TOPIC_EXCHANGE, true, false);
    }

    /**
     * websocket 队列
     *
     * @return org.springframework.amqp.core.Queue
     * @author tanyujie
     * @date 2022/9/14 11:53
     * @since v1.0
     */
    @Bean
    public Queue wsTopicQueue() {
        return new Queue(WebSocketConstant.WS_TOPIC_QUEUE, true, false, false);
    }

    /**
     * websocket 通过KEY绑定交换机与队列
     *
     * @return org.springframework.amqp.core.Binding
     * @author tanyujie
     * @date 2022/9/14 11:53
     * @since v1.0
     */
    @Bean
    public Binding wsTopicBinding() {
        return BindingBuilder.bind(wsTopicQueue()).to(wsTopicExchange()).with(WebSocketConstant.WS_TOPIC_KEY);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmqProperties.getHost(), rabbitmqProperties.getPort());
        connectionFactory.setUsername(rabbitmqProperties.getUsername());
        connectionFactory.setPassword(rabbitmqProperties.getPassword());
        connectionFactory.setVirtualHost(rabbitmqProperties.getVirtualHost());
        // 开启   发送消息回调,必须要设置
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        //设置开启Mandatory,才能触发回调函数,无论消息推送结果怎么样都强制调用回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("ConfirmCallback:     " + "相关数据：" + correlationData);
            log.info("ConfirmCallback:     " + "确认情况：" + ack);
            log.info("ConfirmCallback:     " + "原因：" + cause);
        });

        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("ReturnCallback:     " + "消息：" + message);
            log.info("ReturnCallback:     " + "回应码：" + replyCode);
            log.info("ReturnCallback:     " + "回应信息：" + replyText);
            log.info("ReturnCallback:     " + "交换机：" + exchange);
            log.info("ReturnCallback:     " + "路由键：" + routingKey);
        });

        return rabbitTemplate;
    }

    /**
     * 接受消息的监听，这个监听会接受消息队列topicQueue的消息
     * 针对消费者配置
     *
     * @return org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
     * @author tanyujie
     * @date 2022/9/15 14:32
     * @since v1.0
     */
    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer() {
        // 从连接工厂创建一个监听器容器（强制）
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        // 设置要从中接收消息的队列的名称
        container.setQueues(wsTopicQueue());
        // 设置是否将监听器 Rabbit Channel 公开给已注册的ChannelAwareMessageListener以及org.springframework.amqp.rabbit.core.RabbitTemplate调用。
        container.setExposeListenerChannel(true);
        // 设置消费者数量的上限；默认为“并发消费者”。消费者将按需添加。不能小于concurrentConsumers 。
        container.setMaxConcurrentConsumers(1);
        // 指定要创建的并发使用者的数量。默认值为 1。
        container.setConcurrentConsumers(1);
        // 设置为手动ack
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            // 消息
            byte[] body = message.getBody();
            String msg = new String(body);
            log.info("rabbitmq ws topic queue 收到消息 : " +msg);
            // 发送消息
            if (webSocketMessageService.sendMsg(msg)){
                log.info("消息处理成功！ 已经推送到websocket！");
                // 手动ack
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            }
        });
        return container;
    }

}
