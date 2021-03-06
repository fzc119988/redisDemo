package com.ssm.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ssm.clent.RedisCache;
import com.ssm.mapper.UserMapper;
import com.ssm.model.User;
import com.ssm.service.UserInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
public class UserInfoImpl implements UserInfo {
    public static final Log LOG = LogFactory.getLog(UserInfo.class);
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserMapper mapper;

    private void times(String time) {
        Jedis jedis = redisCache.getResource();
        Long total = jedis.hincrBy("number", time, 1);
        System.out.println("这一分钟内的访问数量为：" + total);
        redisCache.returnResource(jedis);
    }

    private void sum() {
        String math = "1";
        Jedis jedis = redisCache.getResource();
        Long total = jedis.incr(math);
        System.out.println("总的访问数量为：" + total);
        redisCache.returnResource(jedis);
    }

    private void consumerSum(String time, String userValue) {
        Jedis jedis = redisCache.getResource();
        String user = "consumer" + time;
        jedis.lpush("allUser",user);
        if (userValue != null) {
            jedis.lpush(user, userValue);
            List<String> list = jedis.lrange(user, 0, -1);
            for (String p : list) {
                System.out.println(p);
            }
        }else {
            System.out.println("所访问的对象为空！");
        }
        redisCache.returnResource(jedis);
    }

    public String selectUser(int id) {
        Date date = new Date();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
        String userId = Integer.toString(id);
        Jedis jedis = redisCache.getResource();
        String userValue = jedis.get(userId);
        if (userValue == null) {
            User user = mapper.findUserById(id);
            if (user != null) {
                userValue = JSON.toJSONString(user);
                jedis.set(userId, userValue);
                System.out.println("有新的数据缓存上了");
            }
        }
        times(time);
        sum();
        consumerSum(time, userValue);
        redisCache.returnResource(jedis);
        return userValue;
    }

}