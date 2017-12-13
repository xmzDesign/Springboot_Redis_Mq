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
 * @Date 2017/12/13 17:10
 */
@Component
@RabbitListener(queues = QueueConstant.SPIKE_FAIL_QUENUE)
public class SpikeFail {
    @RabbitHandler
    public void process(String info){
        System.out.println("抢购失败的消息通知"+info);
    }

}
