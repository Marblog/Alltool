package com.ai.tool.config

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
import javax.annotation.Resource

@Component
class RedisUtil {
    @Resource
    private lateinit var redisTemplate: RedisTemplate<String, Any>
    fun set(key: String, value: Any, timeout: Long = -1, timeUnit: TimeUnit = TimeUnit.SECONDS) {
        val ops: ValueOperations<String, Any> = redisTemplate.opsForValue()
        ops.set(key, value)
        if (timeout > 0) {
            redisTemplate.expire(key, timeout, timeUnit)
        }
    }

    // 常用操作 - 根据键获取值
    fun get(key: String): Any? {
        val ops: ValueOperations<String, Any> = redisTemplate.opsForValue()
        return ops.get(key)
    }

    // 常用操作 - 删除指定键的键值对
    fun delete(key: String): Boolean {
        return redisTemplate.delete(key)
    }

    // 增加操作 - 递增指定键的值，并返回递增后的值
    fun increment(key: String, delta: Long = 1): Long? {
        val ops: ValueOperations<String, Any> = redisTemplate.opsForValue()
        return ops.increment(key, delta)
    }

    // Hash 操作 - 设置 Hash 表中的字段值
    fun hashSet(key: String, field: String, value: Any) {
        redisTemplate.opsForHash<String, Any>().put(key, field, value)
    }

    // Hash 操作 - 获取 Hash 表中的字段值
    fun hashGet(key: String, field: String): Any? {
        return redisTemplate.opsForHash<String, Any>().get(key, field)
    }

    // Set 操作 - 添加一个或多个元素到集合中
    fun setAdd(key: String, vararg values: Any): Long? {
        return redisTemplate.opsForSet().add(key, *values)
    }

    // Set 操作 - 获取集合的大小
    fun setSize(key: String): Long? {
        return redisTemplate.opsForSet().size(key)
    }

    // 累加生成次数并返回当前次数
    fun incrementAndGetCurrentCount(key: String, timeout: Long): Long? {
        val count = redisTemplate.opsForValue().increment(key, 1)
        if (count == 1L) {
            // 设置过期时间，限制半小时内继续生成
            redisTemplate.expire(key, timeout, TimeUnit.MINUTES)
        }
        return count
    }

    // 获取生成次数
    fun getCurrentCount(key: String): Long {
        return redisTemplate.opsForValue().get(key) as? Long ?: 0
    }

}
