package com.springbootmybatis.procedure.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
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


    /**
     * @param key 健
     * @param field 属性
     * @param value 值
     * @return
     */
    public boolean hset(String key, String field, Object value) {
        try {
            redisTemplate.opsForHash().put(key, field, value);
            return true;
        }catch (Exception e){
            log.error("redis hset eror,key:{},field:{},value:{}",key,field,value);
            return false;
        }
    }


    /**
     * @param key 键
     * @param map 对应多个键值
     * @return
     */
    public boolean hmset(String key, Map<Object,Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        }catch (Exception e){
            log.error("redis hmset eror,key:{},value:{},exception:{}",key,map,e);
            return false;
        }
    }


    /**
     * @param key 获得该key下的所有键值对
     * @return
     */
    public Map<Object, Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 获取key中field属性的值
     * @param key
     * @param field
     * @return
     */
    public Object hget(String key,String field){
        return redisTemplate.opsForHash().get(key,field);
    }

    /**
     *删除key中的属性
     * @param key
     * @param fields
     */
    public void hdel(String key,Object...fields){
        redisTemplate.opsForHash().delete(key,fields);
    }


}
