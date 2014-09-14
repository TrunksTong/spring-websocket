package com.online.spring.websocket.util.redis;

import java.util.HashSet;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

/**
 * Jedis工具类
 * 
 * @author tongyufu
 *
 */
public class JedisUtils {

    private static JedisPool         jedisPool;
    private static JedisSentinelPool jedisSentinelPool;

    /**从连接池获取redis连接*/
    public synchronized static Jedis getJedis() {
        if (jedisPool == null) {
            jedisPool = new JedisPool("192.168.0.188", 6376);
        }
        return jedisPool.getResource();
    }

    /**从哨兵获取redis连接*/
    public synchronized static Jedis getJedisFromSentinel() {
        if (jedisSentinelPool == null) {
            JedisPoolConfig poolConfig = JedisUtils.createPoolConfig(300, 1000, 300, 300);
            Set<String> sentinels = new HashSet<String>();
            sentinels.add("192.168.0.188:26379");
            jedisSentinelPool = new JedisSentinelPool("devmaster", sentinels, poolConfig, 5000);
        }
        return jedisSentinelPool.getResource();
    }

    /**
     * 快速设置JedisPoolConfig, 不执行idle checking。
     */
    public static JedisPoolConfig createPoolConfig(int maxIdle, int maxTotal) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setTimeBetweenEvictionRunsMillis(-1);
        return poolConfig;
    }

    /**
     * 快速设置JedisPoolConfig, 设置执行idle checking的间隔和可被清除的idle时间.
     * 默认的checkingIntervalSecs是30秒，可被清除时间是60秒。
     */
    public static JedisPoolConfig createPoolConfig(int maxIdle, int maxTotal,
                                                   int checkingIntervalSecs,
                                                   int evictableIdleTimeSecs) {
        JedisPoolConfig poolConfig = createPoolConfig(maxIdle, maxTotal);

        poolConfig.setTimeBetweenEvictionRunsMillis(checkingIntervalSecs * 1000);
        poolConfig.setMinEvictableIdleTimeMillis(evictableIdleTimeSecs * 1000);
        return poolConfig;
    }
}
