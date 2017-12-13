package com.hdkj.rabbitmq.config;

import com.hdkj.rabbitmq.constant.QueueConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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
        connectionFactory.setPublisherConfirms(true); // 必须要设置
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


}
