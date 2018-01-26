package com.basicfu.system.common.datasource

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.stereotype.Component

/**
 * @author basicfu
 * @date 2018/1/26
 */
@Component
class RedisConfig {
    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<*, *> {
        val redisTemplate = RedisTemplate<Any,Any>()
        redisTemplate.connectionFactory = redisConnectionFactory
        val fastJson2JsonRedisSerializer = FastJsonRedisSerializer(Any::class.java)
        val stringRedisSerializer = StringRedisSerializer()
        redisTemplate.hashKeySerializer=stringRedisSerializer
        redisTemplate.hashValueSerializer=fastJson2JsonRedisSerializer
        redisTemplate.keySerializer = stringRedisSerializer
        redisTemplate.valueSerializer = fastJson2JsonRedisSerializer
        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }
}
