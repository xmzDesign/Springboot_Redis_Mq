package com.example.manage.service.Implement;

import com.example.manage.service.SpikeBizService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xuminzhe
 * @version V1.0
 * @Project study
 * @Package com.example.manage.service.Implement
 * @Description
 * @Date 2017/12/12 16:41
 */
@Service
public class SpikeBizServiceImplement implements SpikeBizService {

//    @Resource(name = "redisTemplate")
//    private RedisTemplate<String,Integer> stringRedisTemplate;

    @Resource(name = "jsonRedisTemplate")
    private RedisTemplate<String, Object> stringObjectRedisTemplate;

    public static final String REIDS_KEY="watchkeys";

    @Override
    public void spike() {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        stringObjectRedisTemplate.opsForValue().set(REIDS_KEY,100);
        System.out.println("设置watchkeys为"+stringObjectRedisTemplate.opsForValue().get(REIDS_KEY));
        for(int i=0;i<1000;i++){//设置1000个人来发起抢购
            executorService.execute(new MyRunnable("user"+getRandomString(6)));
        }

    }

    private static String getRandomString(int length){
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    class MyRunnable implements  Runnable{

        String userinfo;
        public MyRunnable() {
        }
        public MyRunnable(String uinfo) {
            this.userinfo=uinfo;
        }

        @Override
        public void run() {
            System.out.println("现在的watchkeys为"+stringObjectRedisTemplate.opsForValue().get(REIDS_KEY));
            //监视一个(或多个) key ，如果在事务执行之前这个(或这些) key 被其他命令所改动，那么事务将被打断。
            stringObjectRedisTemplate.watch(REIDS_KEY);
            Integer watchkeys = (Integer) stringObjectRedisTemplate.opsForValue().get(REIDS_KEY);
            int size=watchkeys;
            if(size<=100&&size>=1){
                stringObjectRedisTemplate.multi();
                Integer watchkeys2 = (Integer) stringObjectRedisTemplate.opsForValue().get(REIDS_KEY);
                //System.out.println("现在的watchkeys为"+watchkeys2);
                stringObjectRedisTemplate.opsForValue().increment(REIDS_KEY,-1);
                List<Object> exec = stringObjectRedisTemplate.exec();// 提交事务，如果此时watchkeys被改动了，则返回null
                if(CollectionUtils.isEmpty(exec)){
                    String failuserifo = "fail"+userinfo;
                    String failinfo="用户：" + failuserifo + "商品争抢失败，抢购失败";
                    System.out.println(failinfo);
                    stringObjectRedisTemplate.opsForValue().set(failuserifo,failinfo,60);
                }else{
                    for(Object succ : exec){
                        String succuserifo ="succ"+succ.toString() +userinfo ;
                        String succinfo="用户：" + succuserifo + "抢购成功，当前抢购成功人数:"
                                + (1-(size-100));
                        System.out.println(succinfo);
                        /* 抢购成功业务逻辑 */
                        stringObjectRedisTemplate.opsForValue().set(succuserifo, succinfo,60);
                    }
                }
            }else {
                String failuserifo ="kcfail" +  userinfo;
                String failinfo1="用户：" + failuserifo + "商品被抢购完毕，抢购失败";
                System.out.println(failinfo1);
                stringObjectRedisTemplate.opsForValue().set(failuserifo, failinfo1,60);
            }

        }
    }


}
