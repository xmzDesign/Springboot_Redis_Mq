package com.hdkj.rabbitmq.config;

import com.hdkj.rabbitmq.constant.QueueConstant;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuminzhe
 * @version V1.0
 * @Project study
 * @Package com.hdkj.rabbitmq.config
 * @Description 另一种配置
 * @Date 2017/12/7
 */
@Configuration
public class RabbitMqConfig2 {
    /**
     * 配置链接信息
     * @return
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("106.14.179.201",5673);
        connectionFactory.setUsername("xmz");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("/demo");
        connectionFactory.setPublisherConfirms(true); // 必须要设置，进行消息的回调。

        return connectionFactory;
    }

    @Bean
    public Queue Queue() {
        return new Queue("hello");
    }

    @Bean
    public Queue Queue2() {
        return new Queue("neo");
    }

    @Bean
    public Queue Queue3() {
        return new Queue("object");
    }

    /**
     * 需要将ACK修改为手动确认，避免消息在处理过程中发生异常造成被误认为已经成功消费的假象。
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer messageContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
        container.setQueues(Queue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);//消息确认后才能删除
        container.setPrefetchCount(5);//每次处理5条消息
        container.setMessageListener(new ChannelAwareMessageListener() {

            public void onMessage(Message message, com.rabbitmq.client.Channel channel) throws Exception {
                byte[] body = message.getBody();
                System.out.println("消费端接收到消息 : " + new String(body));
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        });
        return container;
    }



}
