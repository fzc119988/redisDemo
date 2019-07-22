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
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

@Service
public class UserInfoImpl implements UserInfo {
    public static final Log LOG = LogFactory.getLog(UserInfo.class);
    @Autowired(required = true)
    private UserMapper mapper;

    @Autowired(required = true)
    private RedisCache redisCache;

    public String selectAll(int id) {
        User user;
        String Id = Integer.toString(id);
        Jedis jedis = redisCache.getResource();
        String string = jedis.get(Id);
        if (string==null) {
            user = mapper.findUserById(id);
            String us=JSON.toJSONString(user);
            String userId = JSON.toJSONString(Id);
            jedis.set(userId, us);
            System.out.println("缓存上了");
        }
        return string ;
    }

}