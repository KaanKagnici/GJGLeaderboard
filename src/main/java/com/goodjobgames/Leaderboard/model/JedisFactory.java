package com.goodjobgames.Leaderboard.model;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

import java.net.URI;
import java.net.URISyntaxException;

class JedisFactory {
     private static URI redisURI;
     private static JedisPool jedisPool;
     private static JedisFactory instance = new JedisFactory();
     private static Jedis jedis = new Jedis();


     // If you want to configure system with multiple redis instances
     // or in an environment where you have to use JedisPool
     // Just uncomment the parts in JedisFactory() and getResource()
     private JedisFactory() {
//         try {
//             redisURI = new URI(System.getenv("REDISTOGO_URL"));
//             jedisPool = new JedisPool(new JedisPoolConfig(),
//                     redisURI.getHost(),
//                     redisURI.getPort(),
//                     Protocol.DEFAULT_TIMEOUT,
//                     redisURI.getUserInfo().split(":", 2)[1]);
//         } catch (URISyntaxException e) {
//             e.printStackTrace();
//         }
     }


     public Jedis getResource() {
         // return jedisPool.getResource();
         return jedis;
     }

     public static JedisFactory getInstance() {

         return instance;
     }
}
