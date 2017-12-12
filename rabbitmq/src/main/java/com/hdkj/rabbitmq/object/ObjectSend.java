package com.hdkj.rabbitmq.object;

import com.hdkj.rabbitmq.entity.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xuminzhe
 * @version V1.0
 * @Project study
 * @Package com.hdkj.rabbitmq.object
 * @Description
 * @Date 2017/12/8 16:24
 */
@Component
public class ObjectSend {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(User user){
        System.out.println("Sender object: " + user.toString());
        this.rabbitTemplate.convertAndSend("object",user);
    }
}
