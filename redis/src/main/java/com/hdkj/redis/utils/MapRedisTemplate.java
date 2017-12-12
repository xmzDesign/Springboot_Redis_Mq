package com.hdkj.redis.utils;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author xuminzhe
 * @version V1.0
 * @Project study
 * @Package com.hdkj.redis.utils
 * @Description redis工具类
 * @Date 2017/12/4
 */
@Component("mapRedisTemplate")
public class MapRedisTemplate {

    @Resource(name = "redisTemplate")
    private RedisTemplate<String,Object> redisTemplate;

    public Boolean hasKey(String key, Object hashKey) {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        return ops.hasKey(key, hashKey);
    }

    public Boolean delete(String key) {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        Long l = ops.delete(key);
        return l == 1 ? Boolean.TRUE : Boolean.FALSE;
    }

    public Boolean delete(String key, Object hashKey) {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        Long l = ops.delete(key, hashKey);
        return l > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    public Object get(String key, Object hashKey) {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        return ops.get(key, hashKey);
    }

    public Map<Object, Object> entries(String key) {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        return ops.entries(key);
    }

    public Set<Object> keys(String key) {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        return ops.keys(key);
    }

    public List<Object> values(String key) {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        return ops.values(key);
    }

    public Long size(String key) {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        return ops.size(key);
    }

    public void put(String key, Object hashKey, Object value) {

        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        ops.put(key, hashKey, value);
    }

    public void put(String key, Object hashKey, Object value,Long expireIn) {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        ops.put(key, hashKey, value);
        redisTemplate.expire(key, expireIn, TimeUnit.MILLISECONDS);
    }

    public void putIfAbsent(String key, Object hashKey, Object value) {
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        ops.putIfAbsent(key, hashKey, value);
    }

}
