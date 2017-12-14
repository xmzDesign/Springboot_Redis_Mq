package com.hdkj.rabbitmq.hello;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @author xuminzhe
 * @version V1.0
 * @Project study
 * @Package com.hdkj.rabbitmq.controller
 * @Description
 * @Date 2017/12/7
 */
@Controller
public class HelloSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @RequestMapping(value = "hello")
    @ResponseBody
    public String  send() {
        String context = "hello " + new Date();

            System.out.println("Sender : " + context+"******");
            this.rabbitTemplate.convertAndSend("hello", context+"******");

        return "success";
        }

}
