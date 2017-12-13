package com.example.manage.config;

import com.example.manage.entity.QueueConstant;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xuminzhe
 * @version V1.0
 * @Project study
 * @Package com.example.manage.config
 * @Description
 * @Date 2017/12/13 18:32
 */
@Configuration
public class RabbitmqConfig {
    @Bean
    public Queue spikeSucc(){
        return new Queue(QueueConstant.SPIKE_SUCCESS_QUENUE);
    }
    @Bean
    public Queue spikeFail(){
        return new Queue(QueueConstant.SPIKE_FAIL_QUENUE);
    }
}
