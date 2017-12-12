package com.hdkj.redis.config;

import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuminzhe
 * @version V1.0
 * @Project study
 * @Package com.hdkj.redis.config
 * @Description
 * @Date 2017/12/4
 */
//@Configuration
@EnableCaching
@Import(value = RedisAutoConfiguration.class)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfiguration {
    @Inject
    private RedisProperties properties;

    @Bean
    public RedisConnectionFactory jedisConnectionFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(properties.getPool().getMaxActive());
        poolConfig.setMaxIdle(properties.getPool().getMaxIdle());
        poolConfig.setMaxWaitMillis(properties.getPool().getMaxWait());
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnCreate(true);
        poolConfig.setTestWhileIdle(true);
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);
        jedisConnectionFactory.setHostName(properties.getHost());
        if (null != properties.getPassword()) {
            jedisConnectionFactory.setPassword(properties.getPassword());
        }
        jedisConnectionFactory.setDatabase(properties.getDatabase());
        jedisConnectionFactory.setPort(properties.getPort());

        //其他配置，可再次扩展
        return jedisConnectionFactory;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate redisTemplate() {
        RedisTemplate redisTemplate = new RedisTemplate();

        redisTemplate.setConnectionFactory(jedisConnectionFactory());

//        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

        redisTemplate.afterPropertiesSet();
        redisTemplate.setEnableTransactionSupport(true);

        return redisTemplate;
    }

    /**
     * 分布式环境下多台redis的配置
     * @return
     */
    @Bean
    public ShardedJedisPool shardedJedisPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(properties.getPool().getMaxActive());
        jedisPoolConfig.setMaxIdle(properties.getPool().getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(properties.getPool().getMaxWait());
        List<JedisShardInfo> jedisShardInfoList = new ArrayList<>();
        JedisShardInfo jedisShardInfo = new JedisShardInfo(properties.getHost(), properties.getPort());
        jedisShardInfo.setPassword(properties.getPassword());
        jedisShardInfoList.add(jedisShardInfo);
        return new ShardedJedisPool(jedisPoolConfig, jedisShardInfoList);
    }


}
