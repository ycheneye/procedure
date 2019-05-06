package com.springbootmybatis.procedure.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * @作者 chenyi
 * @date 2019/5/5 14:46
 */

@Component
@Slf4j
public class RedisUtil {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 原子操作：设值的同时并设置过期时间
     * @param key
     * @param value
     * @param expire 过期时间
     * @return
     */
    public boolean setex(String key,Object value ,Long expire){
        try{
            redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            log.info("失败信息 =》 {}", e);
            return false;
        }
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public Object get(String key){
        return  key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除key对应的值，有可能需要批量删除，所以传一个可变参数
     * @param key
     */
    public void del(String ...key){
        if (key.length > 0 && key != null)
        redisTemplate.delete(CollectionUtils.arrayToList(key));
    }
}
