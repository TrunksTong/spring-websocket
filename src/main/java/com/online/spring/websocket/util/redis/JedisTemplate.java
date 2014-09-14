package com.online.spring.websocket.util.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTemplate {

    public static void main(String[] args) {
        JedisPool pool = new JedisPool("192.168.0.188", 6376);
        Jedis jedis = pool.getResource();
        String key = "user.name";
        jedis.set(key, "仝玉甫");
        System.out.println(jedis.get(key));
    }
    
}
