package com.hdkj.rabbitmq;

import com.hdkj.rabbitmq.entity.User;
import com.hdkj.rabbitmq.object.ObjectSend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xuminzhe
 * @version V1.0
 * @Project study
 * @Package com.hdkj.rabbitmq
 * @Description
 * @Date 2017/12/8 17:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ObjectTest {
    @Autowired
    private ObjectSend objectSend;

    @Test
    public void objectsend() throws Exception {
        User user=new User();
        user.setName("xmz");
        user.setPassword("123456");
        objectSend.send(user);
    }
}
