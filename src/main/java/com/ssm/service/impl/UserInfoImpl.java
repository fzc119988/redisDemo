package com.ssm.service.impl;

import com.ssm.clent.RedisCache;
import com.ssm.service.UserInfo;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ssm.mapper.UserMapper;
import com.ssm.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisHash;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class UserInfoImpl implements UserInfo {
    public static final Log LOG = LogFactory.getLog(UserInfo.class);
    @Autowired(required = true)
    private UserMapper mapper;

    @Autowired(required = true)
    private RedisCache redisCache;


    public void times(int id) {
        Date date = new Date();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
        String userId = Integer.toString(id);
        Jedis jedis = redisCache.getResource();
        String user = jedis.get(userId);
        Long total = jedis.incr(time);
        String math = String.valueOf(total);
        jedis.hset("number", time, math);
        jedis.lpush("consumer",user);
        List<String> list = jedis.lrange( "consumer", 0, -1 );
        System.out.println(total);
//      Set<String> products = jedis.hkeys("consumer");
        for (String p : list) {
            System.out.println(p);
        }
    }

    public String selectUser(int id) {
        User user;
        String userId = Integer.toString(id);
        Jedis jedis = redisCache.getResource();
        String userValue = jedis.get(userId);
        if (userValue == null) {
            user = mapper.findUserById(id);
            if (user == null) {
                return userValue;
            } else {
                String userJson = JSON.toJSONString(user);
                jedis.set(userId, userJson);
                System.out.println("缓存上了");
            }
        }
        times(id);
        redisCache.returnResource(jedis);
        return userValue;
    }
}