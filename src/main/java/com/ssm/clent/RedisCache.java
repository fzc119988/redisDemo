package com.ssm.clent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.io.Serializable;
public class RedisCache implements Serializable {

    /**
     * 日志记录
     */
    private static final Log LOG = LogFactory.getLog(RedisCache.class);

    /**
     * redis 连接池
     */
    private JedisPool pool;
    public void setPool(JedisPool pool) {
        this.pool = pool;
    }



    /**
     * 获取jedis
     */
    public Jedis getResource() {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
        } catch (Exception e) {
            LOG.info("can't get the redis resource");
        }
        return jedis;
    }

    /**
     * 关闭连接
     */
    public void disconnect(Jedis jedis) {
        jedis.disconnect();
    }

    /**
     * 将jedis 返还连接池
     */
    public void returnResource(Jedis jedis) {
        if (null != jedis) {
            try {
                jedis.close();
            } catch (Exception e) {
                LOG.info("can't return jedis to jedisPool");
            }
        }
    }

    /**
     * 无法返还jedispool，释放jedis客户端对象
     */
    public void brokenResource(Jedis jedis) {
        if (jedis != null) {
            try {
                jedis.close();
            } catch (Exception e) {
                LOG.info("can't release jedis Object");
            }
        }
    }
}
