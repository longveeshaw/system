package com.basicfu.common.util

import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer
import org.springframework.data.redis.core.RedisTemplate
import java.util.concurrent.TimeUnit

object RedisUtil {
    private val timeUnit = TimeUnit.MILLISECONDS
    lateinit var redisTemplate: RedisTemplate<Any, Any>
    //使用前必须传入一个已经注入过的redisTemplate
    fun init(redisTemplate:RedisTemplate<Any, Any>){
        this.redisTemplate= redisTemplate
    }

    fun set(k: Any, v: Any) {
        redisTemplate.opsForValue().set(k, v)
    }

    fun get(k: Any): Any? {
        return redisTemplate.opsForValue().get(k)
    }

    fun <T> get(k: Any, clazz: Class<T>): T? {
        redisTemplate.opsForValue().get(k)?.let {
            return (it as JSONObject).toJavaObject(clazz)
        }
        return null
    }

    fun set(key: Any, value: Any, expireTime: Long) {
        redisTemplate.opsForValue().set(key, value)
        expire(key, expireTime)
    }

    fun expire(key: Any, expireTime: Long) {
        redisTemplate.expire(key, expireTime, timeUnit)
    }

    fun del(key: Any) {
        redisTemplate.delete(key)
    }

    fun hSet(key: Any, hk: Any, hv: Any?) {
        redisTemplate.opsForHash<Any, Any>().put(key, hk, hv)
    }

    fun hGet(key: Any, hk: Any): Any? {
        return redisTemplate.opsForHash<Any, Any>().get(key.toString(), hk.toString())
    }

    fun <T> hMSet(key: String, dataMap: Map<String, T>) {
        redisTemplate.opsForHash<Any, Any>().putAll(key, dataMap)
    }

    fun hMSet(key: String, dataMap: Map<Any, Any>, expireTime: Long) {
        redisTemplate.opsForHash<Any, Any>().putAll(key, dataMap)
        expire(key, expireTime)
    }

    fun <T> hGetAll(key: Any,clazz: Class<T>): Map<Any, T> {
        redisTemplate.hashValueSerializer=FastJsonRedisSerializer(clazz)
        return redisTemplate.opsForHash<Any, T>().entries(key)
    }

    fun hDel(key: Any, hk: Any) {
        redisTemplate.opsForHash<Any, Any>().delete(key, hk)
    }

    fun exists(key: Any): Boolean {
        return redisTemplate.hasKey(key)
    }

    fun multi() {
        redisTemplate.multi()
    }

    fun exec() {
        redisTemplate.exec()
    }

    fun increment(key: Any, l: Long): Long {
        return redisTemplate.opsForValue().increment(key, l)
    }

    fun sadd(key: Any, vararg objs: Any): Long {
        return redisTemplate.opsForSet().add(key, *objs)
    }

    fun scard(key: Any): Long {
        return redisTemplate.boundSetOps(key).size()
    }


//    fun setIsExist(k: String, v: String): Boolean? {
//        return redisTemplate.execute<Boolean?>(RedisCallback<Boolean> { redisConnection ->
//            val redisSerializer = redisTemplate.stringSerializer
//            val key = redisSerializer.serialize(k)
//            val value = redisSerializer.serialize(v)
//            redisConnection.setNX(key, value)
//        }) as Boolean
//    }
}
