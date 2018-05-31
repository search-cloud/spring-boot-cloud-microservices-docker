package org.asion.bot.helper;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

public class RedisClientLocal {
    private static JedisPool jedisPool = initialPool();//非切片连接池
    private static Jedis jedis;

    private static RedisClientLocal redisClient = new RedisClientLocal();
    static {
        jedis = getRedisClient().getJedis();
    }

    private RedisClientLocal() {}

    private static RedisClientLocal getRedisClient() {
        return redisClient;
    }

    private Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 初始化非切片池
     */
    private static JedisPool initialPool() {
        // 池基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(200);
        config.setMaxIdle(50);
        config.setMinIdle(5);
        config.setMaxWaitMillis(13000L);
        config.setTestOnBorrow(false);

        jedisPool = new JedisPool(config, "127.0.0.1", 5050);
        return jedisPool;
    }

    public void close() {
        jedisPool.close();
    }

    public static String get(String key) {
        return jedis.get(key);
    }

    public static String get(String key, int dbIndex) {
        jedis.select(dbIndex);
        return jedis.get(key);
    }

    public static Set<String> keys(final String pattern, int dbIndex) {
        jedis.select(dbIndex);
        return jedis.keys(pattern);
    }

    public static void set(String key, Object value) {
        set(key, value, 0);
    }


    public static void set(String key, Object value, int dbIndex) {
        jedis.select(dbIndex);
        jedis.set(key, JSON.toJSONString(value));
    }

//    public static void main(String[] args) {
//
////        String testKey = "test";
////        Long testValue = System.currentTimeMillis();
////        RedisClientLocal.set(testKey, testValue, 1);
////
////        String getTestValue = RedisClientLocal.get("556643932959", 2);
//
////        System.out.println(getTestValue);
//
////        String s = RedisClientLocal.get("https://detail.tmall.hk/item.htm?id=563080330382&skuId=3709563255716&areaId=330106&is_b=1&user_id=3499948688&cat_id=55922002&q&rn=c53169fa179afcd07376383229697214");
////        jedis.select(1);
////        Set<String> keys = jedis.keys("https://detail.tmall.hk/item.htm?id=18806902137&skuId=79616221238&areaId=330106&is_b=1&user_id=1706787496&cat_id=55916006&q=&rn=e2d982847acd283b4b051b236920e9ed");
////
////        System.out.println(s);
//
////        Set<String> keys = RedisClientLocal.keys("*", 3);
////        System.out.println(keys);
////        System.out.println(keys.size());
////
////        keys.forEach(key -> jedis.del(key));
//
//    }

}