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

import java.util.List;

@Service
public class UserInfoImpl implements UserInfo {
    public static final Log LOG = LogFactory.getLog(UserInfo.class);
    @Autowired(required = true)
    private UserMapper mapper;

    @Autowired(required = true)
    private RedisCache redisCache;
    public static final String dateStart="2019/07/24/15/06";
    public void times(int id){
        String userId=Integer.toString(id);
        Jedis jedis=redisCache.getResource();
        Long total=jedis.incr(dateStart);
        jedis.expire(dateStart,20);
        String user=jedis.get(userId);
        System.out.println(user.toString());
        if(jedis.get(dateStart)==null) {
            System.out.println("总访问量为：" + total);
        }
    }

    public String selectUser(int id) {
        User user;
        String userId = Integer.toString(id);
        Jedis jedis = redisCache.getResource();
        String userValue = jedis.get(userId);
        if (userValue==null) {
            user = mapper.findUserById(id);
            if(user==null){
                return userValue ;
            }else {
                String userJson=JSON.toJSONString(user);
                jedis.set(userId, userJson);
                System.out.println("缓存上了");
            }
        }
        times(id);
        return userValue ;
    }
}