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
import redis.clients.jedis.Jedis;

@Service
public class UserInfoImpl implements UserInfo {
    public static final Log LOG = LogFactory.getLog(UserInfo.class);
    @Autowired(required = true)
    private UserMapper mapper;

    @Autowired(required = true)
    private RedisCache redisCache;

    public String selectUser(int id) {
        User user;
        String userId = Integer.toString(id);
        Jedis jedis = redisCache.getResource();
        String userValue = jedis.get(userId);
        if (userValue==null) {
            user = mapper.findUserById(id);
            String userJson=JSON.toJSONString(user);
            jedis.set(userId, userJson);
            System.out.println("缓存上了");
        }
        return userValue ;
    }

}