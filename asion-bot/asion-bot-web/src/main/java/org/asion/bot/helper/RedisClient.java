package org.asion.bot.helper;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

public class RedisClient {
    private JedisPool jedisPool;//非切片连接池
    private ShardedJedisPool shardedJedisPool;//切片连接池
    private static RedisClient redisClient;
    private static final Jedis jedis = getRedisClient().getJedis();


    private RedisClient() {
        initialPool();
        initialShardedPool();
    }

    public static RedisClient getRedisClient() {
        if (redisClient == null) {
            synchronized (RedisClient.class) {
                if (redisClient == null) redisClient = new RedisClient();
            }
        }
        return redisClient;
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public ShardedJedis getShardedJedis() {
        return shardedJedisPool.getResource();
    }

    /**
     * 初始化非切片池
     */
    private void initialPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(200);
        config.setMaxIdle(50);
        config.setMaxWaitMillis(13000L);
        config.setTestOnBorrow(false);

        jedisPool = new JedisPool(config, "192.168.10.85", 6379);
    }

    /**
     * 初始化切片池
     */
    private void initialShardedPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(200);
        config.setMaxIdle(50);
        config.setMaxWaitMillis(1000L);
        config.setTestOnBorrow(false);
        // slave链接
        List<JedisShardInfo> shards = new ArrayList<>();
        shards.add(new JedisShardInfo("192.168.10.85", 6379, "master"));
        // 构造池
        shardedJedisPool = new ShardedJedisPool(config, shards);
    }

    public void Close() {
        jedisPool.close();
        shardedJedisPool.close();
    }

    public static String get(String key) {
        return jedis.get(key);
    }

    public static void set(String key, Object value) {
        jedis.set(key, JSON.toJSONString(value));
    }

}