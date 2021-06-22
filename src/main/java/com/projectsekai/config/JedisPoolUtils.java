package com.projectsekai.config;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName JedisPoolConfig
 * @description
 * @date 2021/6/21 0021 上午 11:44
 **/
public class JedisPoolUtils {
    private static JedisPool jedisPool;

    static {

    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }
}
