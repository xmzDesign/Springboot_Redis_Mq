package com.hdkj.rabbitmq.spike;

import com.hdkj.rabbitmq.constant.QueueConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author xuminzhe
 * @version V1.0
 * @Project study
 * @Package com.hdkj.rabbitmq.spike
 * @Description
 * @Date 2017/12/13 17:08
 */
@Component
@RabbitListener(queues = QueueConstant.SPIKE_SUCCESS_QUENUE)
public class SpikeSucc {
    @RabbitHandler
    public void process(String info){
        System.out.println("抢购成功的消息"+info);
    }

}
