package com.hww.framework.common.tool;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JedisPoolUtil {

    private static final Lock lock=new ReentrantLock();
    private static final JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
    static {
        jedisPoolConfig.setMaxTotal(10);
    }
    private static final JedisPool pool = new JedisPool(jedisPoolConfig, "10.1.11.28", 6379);
    public static Jedis getConnection(){
        lock.lock();
        try {
            return pool.getResource();

        } finally {
            lock.unlock();
        }
    }




}
